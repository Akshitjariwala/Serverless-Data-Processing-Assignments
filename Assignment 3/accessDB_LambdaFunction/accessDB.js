const mysql = require('mysql');

const aws = require('aws-sdk');

var conn = mysql.createConnection({
    host     : 'lambdafunction.cmbmize6ljqo.us-east-1.rds.amazonaws.com',
    user     : 'admin',
    password : 'mydatabase',
    database : 'lambdafunction'
});

const s3 = new aws.S3({ apiVersion: '2006-03-01' });

conn.connect();

exports.handler = async (event, context) => {

    const bucketName = event.Records[0].s3.bucket.name;

    const fileName = decodeURIComponent(event.Records[0].s3.object.key.replace(/\+/g, ' '));

    var getParamas = {
        Bucket:bucketName,
        Key: fileName
    };

    const data = await s3.getObject(getParamas).promise();
    const response = {
        body: data.Body
    };

    const fileData = response.body.toString();

    var jsonData = JSON.parse(fileData);

    conn.query('SELECT * FROM Entity', function (err, rows, fields) {

        if (err)
            throw err;
        else {
            if(rows == null)
            {
                try {
                    for (const [key, value] of Object.entries(jsonData)) {
                        const sql = "INSERT INTO Entity (namedEntity,entityFrequency) VALUES('" + key + "','" + value + "')";
                        insertEntity(sql);
                    }
                } catch (err) {
                    throw err;
                }finally {
                    conn.close;
                }
            }
            else
            {
                for (var i = 0; i < rows.length; i++) {
                    for (const [key, value] of Object.entries(jsonData)) {
                        if (rows[i].namedEntity == key) {
                            jsonData[key] = jsonData[key] + rows[i].entityFrequency;
                            const deleteEntity = "DELETE FROM Entity WHERE namedEntity = '" + rows[i].namedEntity + "'";
                            conn.query(deleteEntity, function (err, result) {
                                if (err) throw err;
                                console.log(result);
                            });
                        }
                    }
                }

                try {
                    for (const [key, value] of Object.entries(jsonData)) {
                        const sql = "INSERT INTO Entity (namedEntity,entityFrequency) VALUES('" + key + "','" + value + "')";
                        insertEntity(sql);
                    }
                } catch (err) {
                    throw err;
                }
                finally {
                    conn.close;
                }
            }
        }

    });
};

function insertEntity(sql) {
    return new Promise((resolve, reject) => {
        conn.query(sql, function (err, result) {
            if (err) {reject(err);}
            else {
                return resolve(result);
            }
        });
    });
}