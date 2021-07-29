var AWS = require("aws-sdk");

var sns = new AWS.SNS({
    accessKeyId:"ASIAVJDCQO5AXA6MZ46R",
    secretAccessKey:"hFqscf4V8LfxANfdulGIBLjHmZm7AB5JjYCy+mwU",
    sessionToken:"FwoGZXIvYXdzEIf//////////wEaDIpxJB39tkaWFWhwvCK/AULRA3VOjzr8UjzpEvmUJK2R1Al9ifEakmv2/qqTEhXqPaWIMqWR3rQuZxj6RGVa9wDcS3HwZOzDPS/DJLb0OHd8DRhl8L91YXfVIPVWi7BsBfFdUsIfWnFwul+BfuTwnue2+ljmVH0rcPpcEHRcy3/+wv+zGHyhDeVvTdGD+7a2GFUbiTgOu8+pqczQ04TKxEnX6rhHM6Z7R3Z3LHGtO+U7PxPJQqOPb0t7Zj0Yq9Fmo6rGMHE2FA4nSnxxubwwKOSQh4gGMi2XsDKsHO4lv8wjEFahJPnzHWfmLXQY/76GhCosLsVEjbak3HVPFKFP2eijqqs=",
});

exports.handler = async (event) => {
    
    var orderMessage = event.Records[0].body;
    
    console.log('Message Received From SQS : '+orderMessage);
    
    var snsParams = {
        Message: JSON.stringify(orderMessage),
        Subject:"HfxFlowers Delivery Notification",
        TopicArn: 'arn:aws:sns:us-east-1:363131336513:HfxFlowers'
    };
    
    var publishedMessage = sns.publish(snsParams).promise();

    return new Promise((resolve, reject) => {
        publishedMessage.then((data) => {
                resolve({data})
        }).catch((error) => { reject(error); });
    });
};
