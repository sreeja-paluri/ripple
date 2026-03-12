#Ripple App API Contracts
##
Base URL:
http://localhost:8080

## Create User
POST /api/users 
- Example:
  - Request
     - {
          "username": "sreeja",
          "email": "sreeja@test.com",
          "password": "123456"
      }
  - Response
    - {
        "status": "success",
        "message": "User created successfully",
        "data": {
            "id": 1,
            "username": "sreeja",
            "email": "sreeja@test.com",
            "createdAt": "2026-03-07T12:00:00"
        }
    }
  
## Get User
GET /api/users/{id}
- Example : GET /api/users/1
  - Response : 
    - {
          "status": "success",
          "message": "User fetched successfully",
          "data": {
          "id": 1,
          "username": "sreeja",
          "email": "sreeja@test.com",
          "createdAt": "2026-03-07T12:00:00"
          }
      }
  - Error(User Not Found)
      - {
        "status": "error",
        "message": "User not found with id: 10",
        "data": null
        }

## Update User
PUT /api/users/{id}
- Example: PUT /api/users/1
  - Request: {
        "username": "updated_user",
        "email": "updated@test.com",
        "password": "123"
    }
  - Response: {
        "status": "success",
        "message": "User updated successfully",
        "data": {
        "id": 1,
        "username": "updated_user",
        "email": "updated@test.com",
        "createdAt": "2026-03-07T12:00:00"
        }
    }

## Delete User
DELETE /api/users/{id}
- Example: DELETE /api/users/1
  - Response: {
        "status": "success",
        "message": "User deleted successfully",
        "data": null
    }
  
