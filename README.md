# BookingSystem

Admin user is seeded
user name: "admin", password: "admin"

curl --request POST \
  --url http://localhost:8080/users/authenticate \
  --header 'content-type: application/json' \
  --data '{
	"name": "admin",
	"password": "admin"
}'

curl --request POST \
  --url http://localhost:8080/users/authenticate \
  --header 'content-type: application/json' \
  --data '{
	"name": "User1",
	"password": "Password1"
}'

curl --request POST \
  --url http://localhost:8080/users/ \
  --header 'content-type: application/json' \
  --data '{
	"name": "User1",
	"password": "Password1"
}'

curl --request POST \
  --url http://localhost:8080/v1/theatres/1/screens \
  --header 'authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIwIiwiZXhwIjoxNjAzMjI0NTgxLCJpYXQiOjE2MDMyMjM2ODF9.pJzgkOv-DLsAl4dXdpa83nbI5Qxlq5J595jlh2PpXGE' \
  --header 'content-type: application/json' \
  --data '{
	"name": "Screen 4",
	"seatLayout": { 
		"seatGroups": [
			{
				"seatClass": "Premium",
				"seatCount": 6,
				"seats": ["a1","a2","a3","a4","a5","a6"]
			},
			{
				"seatClass": "Supreme",
				"seatCount": 6,
				"seats": ["b1","b2","b3","b4","b5","b6"]
			}
		],
		"totalSeats": 10  
	}
}'

curl --request POST \
  --url http://localhost:8080/v1/movie-shows \
  --header 'authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIwIiwiZXhwIjoxNjAzMjI2MDUyLCJpYXQiOjE2MDMyMjUxNTJ9.ai1RB9SP6e-HuL6KxASntNXhDp_vMQoNulkTy1iH-ws' \
  --header 'content-type: application/json' \
  --data '{
	"screenId" : 5,
	"movieName": "Dark Knight",
	"showPrices": [
		{
			"seatClass": "Premium",
			"price": 110
		},
		{
			"seatClass": "Supreme",
			"price": 180
		}
	],
	"startTime": "09:00",
	"endTime": "12:00"
}'

curl --request POST \
  --url http://localhost:8080/v1/shows \
  --header 'authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIwIiwiZXhwIjoxNjAzMjUzNTM4LCJpYXQiOjE2MDMyNTI2Mzh9.qiSmGDj-3RoD8dlLtILB_6xkiQ6h9QQEr_l1S9yZo0w' \
  --header 'content-type: application/json' \
  --data '{
	"movieShowId": 14,
	"fromDate": "2020-10-21",
	"toDate": "2020-10-28"
}'

curl --request POST \
  --url http://localhost:8080/v1/tickets \
  --header 'authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIyOSIsImV4cCI6MTYwMzI2Mjk5NCwiaWF0IjoxNjAzMjU5Mzk0fQ.dI0nNTrcYvdtVAVvj2pMh_VHHFEln6XhfGcZDD-WhzY' \
  --header 'content-type: application/json' \
  --data '{
	"showId": 39,
	"seatDetails": {
		"seatNumbers": ["a5"],
		"className": "Premium"
	}
}'

curl --request PUT \
  --url http://localhost:8080/v1/tickets/68/payment-success \
  --header 'authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIyOSIsImV4cCI6MTYwMzI2Mjk5NCwiaWF0IjoxNjAzMjU5Mzk0fQ.dI0nNTrcYvdtVAVvj2pMh_VHHFEln6XhfGcZDD-WhzY' \
  --header 'content-type: application/json'

  curl --request PUT \
  --url http://localhost:8080/v1/tickets/68/payment-failure \
  --header 'authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIyOSIsImV4cCI6MTYwMzI2Mjk5NCwiaWF0IjoxNjAzMjU5Mzk0fQ.dI0nNTrcYvdtVAVvj2pMh_VHHFEln6XhfGcZDD-WhzY' \
  --header 'content-type: application/json'

  curl --request GET \
  --url http://localhost:8080/v1/theatres/1 \
  --header 'authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIyOSIsImV4cCI6MTYwMzIyOTY5OCwiaWF0IjoxNjAzMjI2MDk4fQ.K5ag1Msgbs84VLqxIoqqK1JIWEUgdczGMp2b1OA7g_s'

  curl --request GET \
  --url 'http://localhost:8080/v1/theatres?pageNum=1' \
  --header 'authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIyOSIsImV4cCI6MTYwMzIyOTY5OCwiaWF0IjoxNjAzMjI2MDk4fQ.K5ag1Msgbs84VLqxIoqqK1JIWEUgdczGMp2b1OA7g_s'

  curl --request GET \
  --url http://localhost:8080/v1/theatres/1/screens/6 \
  --header 'authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIyOSIsImV4cCI6MTYwMzIyOTY5OCwiaWF0IjoxNjAzMjI2MDk4fQ.K5ag1Msgbs84VLqxIoqqK1JIWEUgdczGMp2b1OA7g_s'

  curl --request GET \
  --url 'http://localhost:8080/v1/movie-shows?movie-name=Dark%20Knight' \
  --header 'authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIyOSIsImV4cCI6MTYwMzIyOTY5OCwiaWF0IjoxNjAzMjI2MDk4fQ.K5ag1Msgbs84VLqxIoqqK1JIWEUgdczGMp2b1OA7g_s'

  curl --request GET \
  --url http://localhost:8080/v1/theatres/1/screens/5/movie-shows \
  --header 'authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIyOSIsImV4cCI6MTYwMzIyOTY5OCwiaWF0IjoxNjAzMjI2MDk4fQ.K5ag1Msgbs84VLqxIoqqK1JIWEUgdczGMp2b1OA7g_s'

  curl --request GET \
  --url http://localhost:8080/v1/theatres/1/screens \
  --header 'authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIyOSIsImV4cCI6MTYwMzIyOTY5OCwiaWF0IjoxNjAzMjI2MDk4fQ.K5ag1Msgbs84VLqxIoqqK1JIWEUgdczGMp2b1OA7g_s'

  curl --request GET \
  --url 'http://localhost:8080/v1/shows?movie-show-id=14&from=2020-10-17&to=2020-10-25' \
  --header 'authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIyOSIsImV4cCI6MTYwMzI1OTMyMiwiaWF0IjoxNjAzMjU1NzIyfQ.rH6sbQG5j_3ybfv_DP4Lz4069_gObEQvJz4Au8Fhc2Q' \
  --header 'content-type: application/json'
