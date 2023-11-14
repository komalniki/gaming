# **GAME**

********Steps to Manually Run********

1) Start Docker (systemctl start docker)
2) docker network create game-mysql
3) docker container run --name mysqldb --network game-mysql -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=game -d mysql:8
4) docker image build -t game-jdbc .
5) docker container run --network game-mysql --name game-jdbc-container -p 8080:8080 -d game-jdbc
   
**Stack Used:** 
1. Java, Spring Boot (Microservice Core).
2. MYSQL (DataBase)
3. Spring JPA
4. Flyway (Database Migration and Initial Schema Creation)
5. MyBatis (JPA)

**Post Execution Steps:**
1. No need to create Schema, Flyway is Integrated which will take care of initial schema creation. 
2. User Creation. 
3. Game Creation. 
4. Post Step 2 and 3, Users can Post their Scores and view ScoreBoard Any Time.
5. User Can view below Types of score board:
   1) Game Wise Score for All Users.
   2) Users Score Specific to a game.
   3) Scores for All Games user has played.
   4) Scores for All Games for All Users.
   5) Top 5 Scores for All Games (It only Show top 5 scorers, a single user might be repeated).
   6) Top 5 Scores for a specific Game.

****API's and Responses:****

**User Controller:**

**1) Create User:**
   Curl: `curl -X POST "http://localhost:8087/game/user/create" -H "accept: */*" -H "Content-Type: application/json" -d "{ \"name\": \"User1\"}"`
   Response:`{
              "success": true,
              "body": {
                      "id": 1000,
                      "name": "User1",
                      "creationDate": "2023-11-14T01:54:06.519+00:00",
                      "active": true,
                      "lastModifiedDate": "2023-11-14T01:54:06.520+00:00"
                      }
              }`

**2) Get All Active Users:**
   Curl: `curl -X GET "http://localhost:8087/game/user/getAllActiveUsers" -H "accept: */*"`
   Response: `{
               "success": true,
               "body": [{
                         "id": 1000,
                         "name": "User1",
                         "creationDate": "2023-11-14T01:54:07.000+00:00",
                         "active": true,
                         "lastModifiedDate": "2023-11-14T01:54:07.000+00:00"
                         }]
               }`

**3) Get All Users:**
   Curl: `curl -X GET "http://localhost:8087/game/user/getAllUsers" -H "accept: */*"`
   Response: `{
               "success": true,
               "body": [{
                     "id": 1000,
                     "name": "User1",
                     "creationDate": "2023-11-14T01:54:07.000+00:00",
                     "active": true,
                     "lastModifiedDate": "2023-11-14T01:54:07.000+00:00"
                     }]
               }`

**4) Get User By Id:**
   Curl: `curl -X GET "http://localhost:8087/game/user/getUserById?userId=1000" -H "accept: */*"`
   Response: `{
               "success": true,
               "body": {
                       "id": 1000,
                       "name": "User1",
                       "creationDate": "2023-11-14T01:54:07.000+00:00",
                       "active": true,
                       "lastModifiedDate": "2023-11-14T01:54:07.000+00:00"
                       }
               }`

**5) Update User:**
   Curl: `curl -X POST "http://localhost:8087/game/user/update?userId=1000" -H "accept: */*" -H "Content-Type: application/json" -d "{ \"name\": \"Test User\"}"`
   Response: `{
               "success": true,
               "body": {
                       "id": 1000,
                       "name": "Test User",
                       "creationDate": "2023-11-14T01:54:07.000+00:00",
                       "active": true,
                       "lastModifiedDate": "2023-11-14T02:05:40.091+00:00"
                       }
               }`

**6) Delete User**
   Curl: `curl -X DELETE "http://localhost:8087/game/user/delete?userId=1000" -H "accept: */*"`
   Response: `{
               "success": true
              }`


****Game Controller:****

**1) Create Game:**
   Curl: `curl -X POST "http://localhost:8087/game/game/create" -H "accept: */*" -H "Content-Type: application/json" -d "{ \"name\": \"Game1\"}"`
   Response: `{
               "success": true,
               "body": {
                       "id": 1000,
                       "name": "Game1",
                       "creationDate": "2023-11-14T02:10:14.426+00:00",
                       "active": true,
                       "lastModifiedDate": "2023-11-14T02:10:14.426+00:00"
                       }
               }`

**2) Get All Active Games:**
   Curl: `curl -X GET "http://localhost:8087/game/game/getAllActiveGames" -H "accept: */*"`
   Response: `{
               "success": true,
               "body": [{
                       "id": 1000,
                       "name": "Game1",
                       "creationDate": "2023-11-14T02:10:14.000+00:00",
                       "active": true,
                       "lastModifiedDate": "2023-11-14T02:10:14.000+00:00"
                       }]
               }`

**3) Get All Games:**
   Curl: `curl -X GET "http://localhost:8087/game/game/getAllGames" -H "accept: */*"`
   Response: `{
               "success": true,
               "body": [{
                       "id": 1000,
                       "name": "Game1",
                       "creationDate": "2023-11-14T02:10:14.000+00:00",
                       "active": true,
                       "lastModifiedDate": "2023-11-14T02:10:14.000+00:00"
                       }]
               }`

**4) Get Game By Id:**
   Curl: `curl -X GET "http://localhost:8087/game/game/getGameById?gameId=1000" -H "accept: */*"`
   Response: `{
               "success": true,
               "body": {
                       "id": 1000,
                       "name": "Game1",
                       "creationDate": "2023-11-14T02:10:14.000+00:00",
                       "active": true,
                       "lastModifiedDate": "2023-11-14T02:10:14.000+00:00"
                       }
               }`

**5) Delete Game**
   Curl: `curl -X DELETE "http://localhost:8087/game/game/delete?gameId=1000" -H "accept: */*"`
   Response: `{
               "success": true
              }`


****Score Board Controller:****

**1) Publish Score**
   Curl: `curl -X POST "http://localhost:8087/game/score/publish" -H "accept: */*" -H "Content-Type: application/json" -d "{ \"gameId\": 1001, \"score\": 20, \"userId\": 1001}"`
   Response: `{
               "success": true
              }`

**2) Get Scores for All Games:**
   Curl: `curl -X GET "http://localhost:8087/game/score/game/all" -H "accept: */*"`
   Response: `{
               "success": true,
               "body": {
                      "Game2": [{
                             "userName": "User2",
                             "score": 30,
                             "date": "2023-11-14T02:19:18.000+00:00"
                               },
                             {
                              "userName": "User2",
                              "score": 20,
                              "date": "2023-11-14T02:19:13.000+00:00"
                              },
                             {
                             "userName": "User1",
                             "score": 20,
                             "date": "2023-11-14T02:18:17.000+00:00"
                             }],
                      "Game3": [{
                               "userName": "User2",
                               "score": 50,
                               "date": "2023-11-14T02:19:28.000+00:00"
                               }]
                      }
               }`

**3) Fetch Scores By Game Id:**
   Curl: `curl -X GET "http://localhost:8087/game/score/game?gameId=1001" -H "accept: */*"`
   Response: `{
               "success": true,
               "body": {
                     "Game2": [{
                                "userName": "User2",
                                "score": 30,
                                "date": "2023-11-14T02:19:18.000+00:00"
                                },
                                {
                                 "userName": "User2",
                                 "score": 20,
                                 "date": "2023-11-14T02:19:13.000+00:00"
                                 },
                                 {
                                 "userName": "User1",
                                 "score": 20,
                                 "date": "2023-11-14T02:18:17.000+00:00"
                                 }
                                 ]
                     }
               }`

**4) Fetch Scores for All Users:**
   Curl: `curl -X GET "http://localhost:8087/game/score/user/all" -H "accept: */*"`
   Response: `{
               "success": true,
               "body": {
                       "User2": [{
                                "gameName": "Game3",
                                "score": 50,
                                "date": "2023-11-14T02:19:28.000+00:00"
                                },
                                {
                                "gameName": "Game2",
                                "score": 30,
                                "date": "2023-11-14T02:19:18.000+00:00"
                                },
                                {
                                "gameName": "Game2",
                                "score": 20,
                                "date": "2023-11-14T02:19:13.000+00:00"
                                }
                                ],
                       "User1": [{
                                "gameName": "Game2",
                                "score": 20,
                                "date": "2023-11-14T02:18:17.000+00:00"
                                }]
                       }
               }`

**5) Fetch Score By User Id:**
   Curl: `curl -X GET "http://localhost:8087/game/score/user?userId=1001" -H "accept: */*"`
   Response: `{
              "success": true,
              "body": {
                      "User1": [{
                      "gameName": "Game2",
                      "score": 20,
                      "date": "2023-11-14T02:18:17.000+00:00"
                      }]
                      }
              }`

**6) Fetch Ranking Board:**
   Curl: `curl -X GET "http://localhost:8087/game/score/ranking/board" -H "accept: */*"`
   Response: `{
              "success": true,
              "body": {
                     "Game2": [{
                              "userName": "User2",
                              "score": 30,
                              "date": "2023-11-14T02:19:18.000+00:00"
                              },
                              {
                              "userName": "User2",
                              "score": 20,
                              "date": "2023-11-14T02:19:13.000+00:00"
                              },
                              {
                              "userName": "User1",
                              "score": 20,
                              "date": "2023-11-14T02:18:17.000+00:00"
                              }
                              ],
                     "Game3": [{
                              "userName": "User2",
                              "score": 50,
                              "date": "2023-11-14T02:19:28.000+00:00"
                              }]
                     }
              }`

**7) Fetch Ranking Board By Game Id:**
   Curl:  `curl -X GET "http://localhost:8087/game/score/ranking/board/game?gameId=1001" -H "accept: */*"`
   Response: `{
               "success": true,
               "body": {
                       "Game2": [{
                                 "userName": "User2",
                                 "score": 30,
                                 "date": "2023-11-14T02:19:18.000+00:00"
                                 },
                                 {
                                 "userName": "User2",
                                 "score": 20,
                                 "date": "2023-11-14T02:19:13.000+00:00"
                                 },
                                 {
                                 "userName": "User1",
                                 "score": 20,
                                 "date": "2023-11-14T02:18:17.000+00:00"
                                 }]
                       }
               }`