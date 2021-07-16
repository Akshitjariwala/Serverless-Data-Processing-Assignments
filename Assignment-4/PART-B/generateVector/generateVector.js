const aws = require('aws-sdk');
const csvStringyfier = require('csv-writer').createObjectCsvStringifier;

const s3Client = new aws.S3({
    accessKeyId:"AKIAYLAVVRY6RUIXXCUD",
    secretAccessKey: "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",
});

exports.handler = async (event,context) => {

    console.log("Starting file extraction from S3 bucket.");

    const bucketName = event.Records[0].s3.bucket.name;
    const testBucketName = "sourcetestdatab00866255a4";

    const s3Params = {
        Bucket: bucketName,
    };

    const s3TestParams = {
        Bucket: testBucketName,
    };

    var wordArray = [];
    var wordTestArray = [];

    return await new Promise(() => {

        s3Client.listObjects(s3Params,function(err,data){
            if(err) console.log(err);
            else{
                var contents = data.Contents;
                var jsonObject = [];
                jsonObject.push({current_word:"Current_Word",next_word:"Next_Word",levenshtein_distance:"Levenshtein_distance"});
                contents.forEach(function(content){
                    const fileName = content.Key;

                    var s3Params = {
                        Bucket:bucketName,
                        Key:fileName
                    };

                    s3Client.getObject(s3Params,function(err,data) {
                            if(err) throw err;
                            else{
                                var fileData = data.Body.toString('utf-8');
                                var fileData = fileData.replace(/(\r\n|\n|\r)/gm," ").replace(/[.]/g,'').replace(/[|-|"|(]/g,'').replace(/[|'|*|?]/g,'').replace(/[$|&|%|£|[|)]/g,'').replace(/['s]/g,"s").replace(/[,]/g,'');
                                var word;
                                var stopWords = ["not","all","It","At","is","for","a","this","that","of","it","the","at","on","an","was","are","were","If","with","The","to","This","Is","Are","in","In","its","Its","as","As","and","And"];

                                for(var i=0;i<fileData.length;i++){
                                    if(fileData[i] == ' '){
                                        if(stopWords.includes(word) || !(isNaN(word)) || word === '-'){
                                            word = '';
                                            continue;
                                        } else {
                                            wordArray.push(word);
                                            word = '';
                                        }
                                    }else{
                                        word += fileData[i];
                                    }
                                }

                                var jsonObject = [];
                                jsonObject.push({current_word:'Current_Word',next_word:'Next_Word',levenshtein_distance:'Levenshtein_distance'});

                                try{
                                    wordArray.forEach(function(word,i)
                                        {
                                            if(!(wordArray[i] ==  null || wordArray[i+1] == null)){
                                                const distance = getLevenshteinDistance(wordArray[i],wordArray[i+1]);
                                                jsonObject.push({
                                                    current_word:wordArray[i],
                                                    next_word:wordArray[i+1],
                                                    levenshtein_distance:distance,
                                                });
                                            }

                                        }
                                    );
                                }catch(e){
                                    console.log(e);
                                }
                                finally{
                                    var trainBucket = "traindatab00866255a4";
                                    var trainKey = "trainVector.csv";
                                    createCSV(jsonObject,trainBucket,trainKey);
                                }
                            }
                        }

                    );
                });
            }
        });

        s3Client.listObjects(s3TestParams,function(err,result){
            if(err) throw err;
            else{
                var contents = result.Contents;
                var jsonObject = [];
                jsonObject.push({current_word:"Current_Word",next_word:"Next_Word",levenshtein_distance:"Levenshtein_distance"});
                contents.forEach(function(content){
                    const fileName = content.Key;

                    var s3TestParams = {
                        Bucket:testBucketName,
                        Key:fileName
                    };

                    s3Client.getObject(s3TestParams,function(err,data) {
                            if(err) throw err;
                            else{
                                var fileData = data.Body.toString('utf-8');
                                var fileData = fileData.replace(/(\r\n|\n|\r)/gm," ").replace(/[.]/g,'').replace(/[|-|"|(]/g,'').replace(/[|'|*|?]/g,'').replace(/[$|&|%|£|[|)]/g,'').replace(/['s]/g,"s").replace(/[,]/g,'');
                                var word;
                                var stopWords = ["not","all","It","At","is","for","a","this","that","of","it","the","at","on","an","was","are","were","If","with","The","to","This","Is","Are","in","In","its","Its","as","As","and","And"];

                                for(var i=0;i<fileData.length;i++){
                                    if(fileData[i] == ' '){
                                        if(stopWords.includes(word) || !(isNaN(word)) || word === '-'){
                                            word = '';
                                            continue;
                                        } else {
                                            wordTestArray.push(word);
                                            word = '';
                                        }
                                    }else{
                                        word += fileData[i];
                                    }
                                }

                                var jsonObject = [];
                                jsonObject.push({current_word:'Current_Word',next_word:'Next_Word',levenshtein_distance:'Levenshtein_distance'});

                                try{
                                    wordTestArray.forEach(function(word,i)
                                        {
                                            if(!(wordTestArray[i] ==  null || wordTestArray[i+1] == null)){
                                                const distance = getLevenshteinDistance(wordTestArray[i],wordTestArray[i+1]);
                                                jsonObject.push({
                                                    current_word:wordTestArray[i],
                                                    next_word:wordTestArray[i+1],
                                                    levenshtein_distance:distance,
                                                });
                                            }

                                        }
                                    );
                                }catch(e){
                                    console.log(e);
                                }
                                finally{
                                    var trainBucket = "testdatab00866255a4";
                                    var trainKey = "testVector.csv";
                                    createCSV(jsonObject,trainBucket,trainKey);
                                }
                            }
                        }
                    );
                });
            };
        });

    });

    function createCSV(jsonObject,trainBucket,trainKey) {
        try{
            const stringifier =  csvStringyfier({
                header: [
                    {id: 'current_word',title: 'Current_Word'},
                    {id: 'next_word', title:'Next_Word'},
                    {id: 'levenshtein_distance', title:'Levenshtein_distance'}
                ]
            });

            const fileContent = stringifier.stringifyRecords(jsonObject);

            const params = {
                Bucket: trainBucket,
                Key: trainKey,
                Body: fileContent,
                ContentType: "text/csv",
            };

            s3Client.upload(params, function (s3Err, data) {
                if (s3Err) throw s3Err;
                else {
                    console.log("Successful");
                }
            });

        }catch(e)
        {
            console.log(e);
        }
    }


    function getLevenshteinDistance(word1,word2){
        var metrics = Array(word1.length + 1).fill(null).map(() => Array(word2.length + 1).fill(null));

        for(var i = 0;i<= word1.length; i++){
            metrics[i][0] = i;
        }

        for(var j = 0;j<= word2.length; j++){
            metrics[0][j] = j;
        }

        for(var i=1; i<= word1.length; i++){
            for(var j=1; j<=word2.length; j++){
                if(word1[i-1] === word2[j-1]){

                    metrics[i][j] = Math.min(metrics[i][j-1] + 1,metrics[i-1][j]	+ 1,metrics[i-1][j-1]);
                } else {
                    metrics[i][j] = Math.min(metrics[i][j-1] + 1,metrics[i-1][j]	+ 1,metrics[i-1][j-1] + 1);
                }
            }
        }
        return metrics[word1.length][word2.length];
    }
};
