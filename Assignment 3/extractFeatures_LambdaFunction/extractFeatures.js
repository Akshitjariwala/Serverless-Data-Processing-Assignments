const aws = require('aws-sdk');

const s3 = new aws.S3({
    accessKeyId:"ASIAVJDCQO5A5RGIJL7Q",
    secretAccessKey: "DchcY8cYSnhAGDPbBDi9O3MJsGSLXu5vjPJ+mxDd",
    sessionToken: "FwoGZXIvYXdzEIX//////////wEaDPw31Do+bEVGi5HGMiK/AeX7SOHt8nO4rS7KuMYB7kn6Kn4dAYUwPn03wtOsvH/Jd5Ns7kq9cOiEMV6G7xycplphfLgXcXgnBcnmH7KYI/bJo9pc1IUcZdwCpHgo1uswYlhah4dzzYYWS4YddEv2O/j+iKzW4Z4mfMPvRcdnHKIimhE2T5OPCkyEBVXgykkidlPL1KObny5VH6RqICOshjsmn+KTWixRualuC4QnA0EU/c+EThtMPC3mvBL22a+46M0NniCOWzW6Jpg+mZa3KJX33YYGMi1q8OToHa8PoR/pevK0jDeUfKOFcZOX5mmHgNlgXHAeWvnK/YGcQRExyuH+z+8="
});

exports.handler = async (event,context,callback) => {

    const bucketName = event.Records[0].s3.bucket.name;

    const fileName = decodeURIComponent(event.Records[0].s3.object.key.replace(/\+/g, ' '));

    var newFileName = fileName.substr(0,3)+'ne.txt';

    return await new Promise((resolve, reject) => {
        var getParamas = {
            Bucket:bucketName,
            Key: fileName
        };

        s3.getObject(getParamas,function(err,data)
        {if(err){
            return err;
        }
        else
        {
            //All file data.
            var fileData = data.Body.toString('utf-8');

            //Remove new line from file data.
            var fileData = fileData.replace(/(\r\n|\n|\r)/gm, "").replace(/[.]/g," ").replace(/[,|-|"|(]/g,"").replace(/[|'|*|?]/g, '').replace(/[$|&|%|£|[|)]/g,'').replace(/['s]/g,'s');
            var namedEntities = [];
            var entity;
            var grammer = ["He","She","They","According","The","Other","However","This","In","-","It","At","That","These","If","Others","Likewise","Thus","On","Out","All","During","Without"];

            for(var i = 0;i<fileData.length;i++)
            {
                if(fileData[i] == ' ')
                {
                    if(isNamedEntity(entity))
                    {
                        namedEntities.push(entity);
                    }
                    entity = '';
                }
                else{
                    entity += fileData[i];
                }
            }

            // Remove non NamedEntity words from the array.
            for(var j=0;j<namedEntities.length;j++)
            {
                if(grammer.includes(namedEntities[j]))
                {
                    namedEntities.splice(j,1);
                    j--;
                }
            }

            // Get Named Entity frequency.
            for(var k=0;k<namedEntities.length;k++)
            {
                var count=1;
                var entity = namedEntities[k];
                for(var m = k+1;m<namedEntities.length;m++)
                {
                    if(entity == namedEntities[m])
                    {
                        count++;
                        namedEntities.splice(m,1);
                        m--;
                    }
                }
                namedEntities.splice(k+1,0,count);
                k++;
            }

            //Convert array to JSON array.
            var jsonArray = {};

            while(namedEntities.length>0)
            {
                let array = namedEntities.splice(0,2);
                jsonArray[array[0]] = array[1];
            }

            var arrayToString = JSON.stringify(Object.assign({}, jsonArray));

            uploadFileToNewBucket(arrayToString);
        }
        });
    });

    function uploadFileToNewBucket(arrayToString)
    {
        const s3Bucket = "tagsb00866255";
        const objectName = newFileName;

        try {
            const uploadParams = {
                Bucket: s3Bucket,
                Key: objectName,
                Body: arrayToString
            };

            s3.putObject(uploadParams).promise();
        } catch (error) {
            return 'Error Returned';
        }
    }

    //To check if the entity is Named Entity.
    function isNamedEntity(string)
    {
        string = string.replace(/[0-9]/g,'');
        if(isNaN(string))
        {
            return string.charAt(0) == string.charAt(0).toUpperCase();
        }
        return false;
    }
};