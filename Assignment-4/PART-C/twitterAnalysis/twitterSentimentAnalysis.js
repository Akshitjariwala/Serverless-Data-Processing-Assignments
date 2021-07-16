
const aws = require('aws-sdk');

var comprehend = new aws.Comprehend();

var fs = require('fs');

const s3Client = new aws.S3({
    accessKeyId:"ASIAVWEZTLUW7C3VOLLW",
    secretAccessKey: "wcZBLLDzclk3f3Ww6mvYBTr05sKg8HBdQ6ZDHBCO",
    sessionToken: "FwoGZXIvYXdzEPj//////////wEaDN5qNs+ATdFp80mc3yK/AVe5I7grHoC4AeFMvvviOY+u+HSsMmqbQOtDxjnQsD+l/q3C7DqNLmg54luzVdSqVuNFXT/P6M3IHIZwDysHksFZHimyTtAmfQ7PnXjREgLq6Mz5hhhadI4vx7CmxsC3AW5kbnfnIgr0sI3s9sY5+h7Pmv7NTloM6LfUDNj9XlZpUWNZ9NtYI1WT4rYvOJKrfxsTp0KeXGAx4olOdQPibWHw/bzJrS/PA3DmPnXnPx6lTTuxLNL7YUUSjp0QBIxMKOXKr4cGMi1e+TsOhjSWVj3OHV2CJVfiSHCHyosjSTYHEWn6l+Bp7go4QRMrfHKP37e+0k4="
});

exports.handler = async (event,context) => {

    console.log("Sentiment Analysis Started.");

    var bucketName = event.Records[0].s3.bucket.name;

    var fileName = decodeURIComponent(event.Records[0].s3.object.key.replace(/\+/g, ' '));

    var s3Params = {
        Bucket:bucketName,
        Key: fileName
    };

    console.log(bucketName+" "+fileName);

    var languages = ["en","es","fr","de","it","pt","ar","hi","ja","ko","zh","zh-TW"];

    var sentiments = [];

    var data = await s3Client.getObject(s3Params).promise();

    var tweetData = data.Body.toString('utf-8');

    var tweetArray = [];
    var tempArray = [];

    var tempArray = tweetData.split("\n");
    var tweet;

    for(var line of tempArray){
        if(line.length < 1){
            tweet = tweet.replace(/(\r\n|\n|\r)/gm, "");
            tweetArray.push(tweet);
            tweet='';
        } else {
            tweet += line;
        }
    }

    for(var tweet of tweetArray)
    {
        var bytes = Buffer.byteLength(tweet, "utf-8");
        if(bytes < 5000)
        {
            if(tweet.length > 0)
            {
                var params = {
                    LanguageCode: 'en',
                    TextList: [tweet],
                };

                const sentimentList = await comprehend.batchDetectSentiment(params).promise();
                const sentimentResult = sentimentList.ResultList[0];
                sentiments.push(JSON.stringify(Object.assign({}, sentimentResult)));
            }
        }
    }

    console.log(sentiments);


    var jsonSentiment = JSON.stringify(Object.assign({}, sentiments));

    const outputFile = "sentimentData.json";
    const outputBucket = "outputbucketb00866255";

    var uploadParams = {
        Bucket: outputBucket,
        Key: outputFile,
        Body:jsonSentiment,
    };

    await s3Client.putObject(uploadParams).promise();
};
