import pandas as pandaLoader
import matplotlib.pyplot as plt
from sagemaker import KMeans
from sagemaker import get_execution_role
import boto3
import io
import sagemaker
import csv
import seaborn as sns
import os
import mxnet as mx

executionRole = get_execution_role()
s3 = boto3.client("s3")
bucketName = 'traindatab00866255a4'
testBucketName = 'testdatab00866255a4'
keyName = 'trainVector.csv'
testKeyName = 'testVector.csv'

response = s3.get_object(Bucket=bucketName,Key=keyName)
data = response["Body"].read()
dataframe = pandaLoader.read_csv(io.BytesIO(data), header=0, delimiter=",", low_memory=False)

for data in dataframe.index:
    dataframe.at[data,"Current_word"] = len(dataframe.at[data,"Current_word"])
    dataframe.at[data,"Next_word"] = len(dataframe.at[data,"Next_word"])

s3_input_train = dataframe.values.astype("float32")

num_clusters = 7
kmeans = KMeans(
    role=executionRole,
    instance_count=1,
    instance_type="ml.c4.xlarge",
    output_path="s3://traindatab00866255a4/training/",
    k=num_clusters,
)

kmeans.fit(kmeans.record_set(s3_input_train))

kmeans_predictor = kmeans.deploy(initial_instance_count=1, instance_type="ml.t2.medium")

response = s3.get_object(Bucket=testBucketName,Key=testKeyName)
testdata = response["Body"].read()
testdataframe = pandaLoader.read_csv(io.BytesIO(testdata), header=0, delimiter=",", low_memory=False)

    for data in testdataframe.index:
        testdataframe.at[data,"Current_word"] = len(testdataframe.at[data,"Current_word"])
        testdataframe.at[data,"Next_word"] = len(testdataframe.at[data,"Next_word"])

s3_validation_test = testdataframe.values.astype("float32")

prediction_Output = kmeans_predictor.predict(s3_validation_test)

labels = [o.label["closest_cluster"].float32_tensor.values[0] for o in prediction_Output]

pandaLoader.DataFrame(labels)[0].value_counts()

ax = plt.subplots(figsize=(6, 3))
ax = sns.distplot(labels, kde=False)
plotTitle = "Levenshtein_distance cluster"
ax.set_title(plotTitle, fontsize=12)
plt.show()

bucketKey = "training/kmeans-2021-07-11-17-55-24-050/output/model.tar.gz"

boto3.resource("s3").Bucket(bucketName).download_file(bucketKey, "model.tar.gz")
os.system("tar -zxvf model.tar.gz")
os.system("unzip model_algo-1")

Kmeans_params = mx.ndarray.load("model_algo-1")

cluster_centroids = pandaLoader.DataFrame(Kmeans_params[0].asnumpy())
cluster_centroids.columns = dataframe.columns
cluster_centroids

plt.figure(figsize=(16, 6))
ax = sns.heatmap(cluster_centroids.T, cmap="YlGnBu")
ax.set_xlabel("Cluster")
plt.yticks(fontsize=16)
plt.xticks(fontsize=16)
ax.set_title("Attribute Value by Centroid")
plt.show()

facet = sns.lmplot(data=cluster_centroids, x='Current_word', y='Next_word', hue='Levenshtein_distance', 
                   fit_reg=False, legend=True, legend_out=True)