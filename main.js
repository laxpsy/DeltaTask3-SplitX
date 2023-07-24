const express = require('express'); //importing express
const app = express(); //launching app
const mysql = require('mysql')
const util = require('util')
const bcrypt = require('bcrypt');
require("dotenv").config();



//pulling sensitive information from .env file
const DB_HOST = process.env.DB_HOST
const DB_USER = process.env.DB_USER
const DB_PASSWORD = process.env.DB_PASSWORD
const DB_DATABASE = process.env.DB_DATABASE
const DB_PORT = process.env.DB_PORT
const PORT = process.env.PORT


const db = mysql.createPool({
    host: DB_HOST,
    user: DB_USER,
    password: DB_PASSWORD,
    database: DB_DATABASE,
    port: DB_PORT
});

const quer = util.promisify(db.query).bind(db);

app.use(express.json())

//default pathway
app.get('/', (req, res) => {
    res.send('UP and RUNNING');
});

//create user pathway
app.post('/createUser', async (req, res) => {
    const user = req.body.name
    const hashedPassword = await bcrypt.hash(req.body.password, 10)

    db.getConnection(async (err, connection) => {
        if (err) throw (err);

        console.log("DB connection successfull with ID" + connection.threadId);

        await connection.query('SELECT * FROM userdb WHERE username = ?', [user], async (err, results) => {
            if (err) throw err
            console.log("Search Results: " + results.length)

            //checking if user already exists

            if (results.length != 0) {
                connection.release();
                console.log("========= USER ALREADY EXISTS =========");
                res.sendStatus(409); //code for conflicting request
            }

            else {
                await connection.query('INSERT INTO userdb VALUES (0, ?, ?);', [user, hashedPassword], (err, results) => {
                    connection.release();
                    if (err) throw err;
                    console.log("========= NEW USER SUCCESSFULLY CREATED =========");
                    console.log(results.insertId);
                    res.sendStatus(201); //code for succesfull post request
                })
            }
        }


            //end of insertion query

        )
    })   //end of db connection
}) //end of post request


//login pathway
app.post('/loginUser', async (req, res) => { //i have no idea why but when i change the route endpoint to loginUser the server crashes :thumbsup;;; wait now it doesnt
    const user = req.body.name;
    const userEnteredPassword = req.body.password;

    await db.query('SELECT * FROM userdb WHERE username = ?', [user], async (err, results) => { //db.getConnection not really needed here, singleRequest to Server
        if (err) throw err;

        if (results === 0) {
            console.log("========= USER DOES NOT EXIST =========");
            res.sendStatus(404);
        }
        else {
            if (await bcrypt.compare(userEnteredPassword, results[0].password)) {
                console.log("========= USER AUTHENTICATED SUCCESFULLY =========");
                res.status(200).send(results[0]);
            }
            else {
                console.log("========= USER AUTHENTICATION FAILED ========= ");
                res.send({
                    "userID": -1
                });
            }

        }

    }) //end of selection query


}) //end of post request


app.get('/newSplit', (req, res) => { res.send("Yeah bro pathway running") });

//split specification pathway
app.post('/newSplit', async (req, res) => {
    const userIDs = req.body.userIDs;
    const splitAmount = req.body.splitAmount;
    const splitID = req.body.splitID;
    const split_origin_id = req.body.split_origin_id
    const reason = req.body.reason

    db.getConnection(async (err, connection) => {
        if (err) throw (err);

        console.log("DB connection successfull with ID" + connection.threadId);

        await connection.query('SELECT * FROM splitsdb WHERE splitID = ?', [splitID], async (err, results) => {

            if (results.length != 0) {
                console.log("========= SPLIT ALREADY EXISTS =========");
                res.sendStatus(409);
                connection.release();
            }
            else {
                await connection.query('INSERT INTO splitsdb VALUES (?, ?, ?, ?, ?)', [splitID, splitAmount, userIDs.length, split_origin_id, reason], (err, results) => {
                    if (err) throw (err);
                }) //end of splitDB insertion

                for (let i = 0; i < userIDs.length; i++) {
                    await connection.query('INSERT INTO splitusermapdb VALUES (0, ?, ?, 0)', [splitID, userIDs[i].id], (err, results) => {
                        if (err) throw (err);
                    })
                }
                console.log(`The split of amount ${splitAmount} has been succesfully logged with the ID ${splitID}`);
                connection.release();
                res.send(201);
            }

        }) //end of connection.query 
    }) //end of db.getConnection
}) //end of split specification pathway post request

//split specification pathway based on userID
app.post('/userNetSplitDetails', (req, res) => {
    const userID = req.body.userID;
    const amount = [];
    const userSplitIDsList = [];

    db.getConnection((err, connection) => {
        if (err) throw (err);

        console.log("DB connection successfull with ID" + connection.threadId);

        connection.query('SELECT * FROM splitusermapdb WHERE userID = ? AND settled = 0', [userID], async (err, results) => {
            console.log("Executing query to select from splitusermapdb");
            const temp = results

            if (results.length === 0) {
                console.log("========= NO SPLIT HAS ORIGINATED FROM USER =========");
                res.sendStatus(404);
                connection.release();
            }
            else {
                try {
                    for (let j = 0; j < temp.length; j++) {
                        console.log(`Value of j is ${j}`);

                        userSplitIDsList.push(temp[j].splitID)
                        await new Promise((res, rej) => { connection.query('SELECT * FROM splitsdb WHERE splitID = ?', [temp[j].splitID], (err, results) => {
                            console.log("Query to select SplitID executed");


                            if (results[0].split_origin_id == userID) {
                                amount.push((results[0].split_amount) * (results[0].total_users - 1) / results[0].total_users)
                                console.log("Pushed#1 @ j = " + j);
                                res(results);

                            }
                            else {
                                amount.push(0 - (results[0].split_amount / results[0].total_users))
                                console.log("Pushed#2 @ j = " + j);
                                res(results);

                            }

                        })}

                        )
                    }
                } finally {
                    connection.release()
                    console.log("Code executed");
                    console.log(`Splits of ID ${userID} have been returned successfully`);
                    const toBeSentObject =
                    {
                        userSplitAmountsArray: amount,
                        userAllSplitsID: userSplitIDsList
                    };
                    res.send(toBeSentObject);
                }





            }//end of query

        }





        )
    })

}) //end of db.getConnection
//end of get split request

app.get('/fetchUsers', async (req, res) => {
    db.getConnection(async (err, connection) => {
        if (err) throw (err)
        connection.query('SELECT username, userID FROM userdb', (err, results) => {
            if (err) throw (err)
            res.send(results)
        })
    })
})

app.post('/fetchIndividualSplitDetails', async (req, res) => {
    db.getConnection(async (err, connection) => {
        const splitID = req.body.splitID
        const serListOfIDs = []



        if (err) throw (err)
        await connection.query('SELECT split_amount, split_origin_id, reason FROM splitsdb WHERE splitID = ?', [splitID], async (err, results) => {
            if (err) throw (err)
            const serOriginID = results[0].split_origin_id
            const splitAmonut = results[0].split_amount
            const reason = results[0].reason
            const settled = []

            await (connection.query('SELECT userID, settled FROM splitusermapdb WHERE splitID = ?', [splitID], (err, results) => {
                if (err) throw (err)
                for (let i = 0; i < results.length; i++) {
                    serListOfIDs.push(results[i].userID)
                    settled.push(results[i].settled)
                }

                const responseObject =
                {
                    listOfIDs: serListOfIDs,
                    settled: settled,
                    userOriginId: serOriginID,
                    splitAmonut: splitAmonut,
                    reason: reason
                };
                res.send(responseObject)

            }))

        })
    })
})

app.get('/individualSplitUserDetails', async (req, res) => {
    const splitID = req.body.splitID
    db.getConnection(async (err, connection) => {
        if (err) throw (err)
        connection.query('SELECT userID FROM splitusermapdb WHERE splitID = ?', [splitID], (err, results) => {
            if (err) throw (err)

            if (results.length == 0) {
                console.log("No split with such SplitID exists");
                res.sendStatus(404)
                connection.release()
            }
            else {
                res.send(results)
                connection.release()
            }
        })
    })

})


app.patch('/updateSplitSettle', async (req, res) => {
    console.log(req.body);
    const splitID = req.body.splitID
    const userID = req.body.userID

    db.getConnection((err, connection) => 
    {
        connection.query("UPDATE splitusermapdb SET settled = 1 WHERE splitID = ? and userID = ?", [splitID, userID], (err, results) =>
        {
            if (err) throw (err)
        })
        connection.query("UPDATE splitsdb SET total_users = total_users - 1 WHERE splitID = ?", [splitID], (err, results) => {
            if (err) throw (err)
            connection.release()
        })
    })

    console.log(`Done @ ${userID}, ${splitID}`);
    res.send("Done!") 

})

app.listen(PORT, "127.0.0.1", () => console.log("LISTENING SUCCESSFULLY"));


