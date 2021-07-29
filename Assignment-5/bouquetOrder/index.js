var AWS = require('aws-sdk');

var sqs = new AWS.SQS({region: 'us-east-1',
    accessKeyId:"ASIAVJDCQO5AXA6MZ46R",
    secretAccessKey:"hFqscf4V8LfxANfdulGIBLjHmZm7AB5JjYCy+mwU",
    sessionToken:"FwoGZXIvYXdzEIf//////////wEaDIpxJB39tkaWFWhwvCK/AULRA3VOjzr8UjzpEvmUJK2R1Al9ifEakmv2/qqTEhXqPaWIMqWR3rQuZxj6RGVa9wDcS3HwZOzDPS/DJLb0OHd8DRhl8L91YXfVIPVWi7BsBfFdUsIfWnFwul+BfuTwnue2+ljmVH0rcPpcEHRcy3/+wv+zGHyhDeVvTdGD+7a2GFUbiTgOu8+pqczQ04TKxEnX6rhHM6Z7R3Z3LHGtO+U7PxPJQqOPb0t7Zj0Yq9Fmo6rGMHE2FA4nSnxxubwwKOSQh4gGMi2XsDKsHO4lv8wjEFahJPnzHWfmLXQY/76GhCosLsVEjbak3HVPFKFP2eijqqs=",
});

exports.handler = function(event, context) {
    
    var bouquetSizes = [
        "Small",
        "Regular",
        "Medium",
        "Large",
        "Extra Large"
        ];
        
    var randomBouquetSize;
    var orderMessage;

    randomBouquetSize = bouquetSizes[Math.floor(Math.random()*bouquetSizes.length)];
        
    orderMessage = 'Hello HfxFlowers, I Would like to order a Bouquet of '+randomBouquetSize+' size.';
        
    console.log(orderMessage);
    
    var sqsParams = {
        MessageBody: JSON.stringify(orderMessage),
        QueueUrl: 'https://sqs.us-east-1.amazonaws.com/363131336513/hfxFlower'
    };
    
    sqs.sendMessage(sqsParams, function(err, data) {
        if (err) {
            console.log('Error : Fail Send Message' + err);
        } else {
            console.log('data:', data.MessageId);
        }
    });
};