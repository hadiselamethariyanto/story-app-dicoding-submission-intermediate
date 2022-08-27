package com.example.submissionintermediate.utils

import com.example.submissionintermediate.data.remote.response.FileUploadResponse
import com.example.submissionintermediate.data.remote.response.LoginResponse
import com.example.submissionintermediate.data.remote.response.RegisterResponse
import com.example.submissionintermediate.data.remote.response.StoriesResponse
import com.google.gson.Gson

class ApiServiceJson {

     fun getAllStoriesJson(): String {
        return "{\n" +
                "    \"error\": false,\n" +
                "    \"message\": \"Stories fetched successfully\",\n" +
                "    \"listStory\": [\n" +
                "        {\n" +
                "            \"id\": \"story-FvU4u0Vp2S3PMsFa\",\n" +
                "            \"name\": \"Dimas-1\",\n" +
                "            \"description\": \"Lorem Ipsum\",\n" +
                "            \"photoUrl\": \"https://story-api.dicoding.dev/images/stories/photos-1641623658595_dummy-pic.png\",\n" +
                "            \"createdAt\": \"2022-01-08T06:34:18.598Z\",\n" +
                "            \"lat\": -10.212,\n" +
                "            \"lon\": -16.002\n" +
                "        },{\n" +
                "            \"id\": \"story-FvU4u0Vp2S3PMsFb\",\n" +
                "            \"name\": \"Dimas\",\n" +
                "            \"description\": \"Lorem Ipsum\",\n" +
                "            \"photoUrl\": \"https://story-api.dicoding.dev/images/stories/photos-1641623658595_dummy-pic.png\",\n" +
                "            \"createdAt\": \"2022-01-08T06:34:18.598Z\",\n" +
                "            \"lat\": -10.212,\n" +
                "            \"lon\": -16.002\n" +
                "        },{\n" +
                "            \"id\": \"story-FvU4u0Vp2S3PMsFc\",\n" +
                "            \"name\": \"Dimas\",\n" +
                "            \"description\": \"Lorem Ipsum\",\n" +
                "            \"photoUrl\": \"https://story-api.dicoding.dev/images/stories/photos-1641623658595_dummy-pic.png\",\n" +
                "            \"createdAt\": \"2022-01-08T06:34:18.598Z\",\n" +
                "            \"lat\": -10.212,\n" +
                "            \"lon\": -16.002\n" +
                "        },{\n" +
                "            \"id\": \"story-FvU4u0Vp2S3PMsFd\",\n" +
                "            \"name\": \"Dimas\",\n" +
                "            \"description\": \"Lorem Ipsum\",\n" +
                "            \"photoUrl\": \"https://story-api.dicoding.dev/images/stories/photos-1641623658595_dummy-pic.png\",\n" +
                "            \"createdAt\": \"2022-01-08T06:34:18.598Z\",\n" +
                "            \"lat\": -10.212,\n" +
                "            \"lon\": -16.002\n" +
                "        },{\n" +
                "            \"id\": \"story-FvU4u0Vp2S3PMsFe\",\n" +
                "            \"name\": \"Dimas\",\n" +
                "            \"description\": \"Lorem Ipsum\",\n" +
                "            \"photoUrl\": \"https://story-api.dicoding.dev/images/stories/photos-1641623658595_dummy-pic.png\",\n" +
                "            \"createdAt\": \"2022-01-08T06:34:18.598Z\",\n" +
                "            \"lat\": -10.212,\n" +
                "            \"lon\": -16.002\n" +
                "        },{\n" +
                "            \"id\": \"story-FvU4u0Vp2S3PMsFf\",\n" +
                "            \"name\": \"Dimas\",\n" +
                "            \"description\": \"Lorem Ipsum\",\n" +
                "            \"photoUrl\": \"https://story-api.dicoding.dev/images/stories/photos-1641623658595_dummy-pic.png\",\n" +
                "            \"createdAt\": \"2022-01-08T06:34:18.598Z\",\n" +
                "            \"lat\": -10.212,\n" +
                "            \"lon\": -16.002\n" +
                "        },{\n" +
                "            \"id\": \"story-FvU4u0Vp2S3PMsFg\",\n" +
                "            \"name\": \"Dimas\",\n" +
                "            \"description\": \"Lorem Ipsum\",\n" +
                "            \"photoUrl\": \"https://story-api.dicoding.dev/images/stories/photos-1641623658595_dummy-pic.png\",\n" +
                "            \"createdAt\": \"2022-01-08T06:34:18.598Z\",\n" +
                "            \"lat\": -10.212,\n" +
                "            \"lon\": -16.002\n" +
                "        },{\n" +
                "            \"id\": \"story-FvU4u0Vp2S3PMsFh\",\n" +
                "            \"name\": \"Dimas\",\n" +
                "            \"description\": \"Lorem Ipsum\",\n" +
                "            \"photoUrl\": \"https://story-api.dicoding.dev/images/stories/photos-1641623658595_dummy-pic.png\",\n" +
                "            \"createdAt\": \"2022-01-08T06:34:18.598Z\",\n" +
                "            \"lat\": -10.212,\n" +
                "            \"lon\": -16.002\n" +
                "        },{\n" +
                "            \"id\": \"story-FvU4u0Vp2S3PMsFi\",\n" +
                "            \"name\": \"Dimas\",\n" +
                "            \"description\": \"Lorem Ipsum\",\n" +
                "            \"photoUrl\": \"https://story-api.dicoding.dev/images/stories/photos-1641623658595_dummy-pic.png\",\n" +
                "            \"createdAt\": \"2022-01-08T06:34:18.598Z\",\n" +
                "            \"lat\": -10.212,\n" +
                "            \"lon\": -16.002\n" +
                "        },{\n" +
                "            \"id\": \"story-FvU4u0Vp2S3PMsFj\",\n" +
                "            \"name\": \"Dimas\",\n" +
                "            \"description\": \"Lorem Ipsum\",\n" +
                "            \"photoUrl\": \"https://story-api.dicoding.dev/images/stories/photos-1641623658595_dummy-pic.png\",\n" +
                "            \"createdAt\": \"2022-01-08T06:34:18.598Z\",\n" +
                "            \"lat\": -10.212,\n" +
                "            \"lon\": -16.002\n" +
                "        }\n" +
                "    ]\n" +
                "}"
    }

    fun getSecondPageStoriesJson():String{
        return "{\n" +
                "    \"error\": false,\n" +
                "    \"message\": \"Stories fetched successfully\",\n" +
                "    \"listStory\": [\n" +
                "        {\n" +
                "            \"id\": \"story-FvU4u0Vp2S3PMsFk\",\n" +
                "            \"name\": \"Dimas\",\n" +
                "            \"description\": \"Lorem Ipsum\",\n" +
                "            \"photoUrl\": \"https://story-api.dicoding.dev/images/stories/photos-1641623658595_dummy-pic.png\",\n" +
                "            \"createdAt\": \"2022-01-08T06:34:18.598Z\",\n" +
                "            \"lat\": -10.212,\n" +
                "            \"lon\": -16.002\n" +
                "        },{\n" +
                "            \"id\": \"story-FvU4u0Vp2S3PMsFl\",\n" +
                "            \"name\": \"Dimas\",\n" +
                "            \"description\": \"Lorem Ipsum\",\n" +
                "            \"photoUrl\": \"https://story-api.dicoding.dev/images/stories/photos-1641623658595_dummy-pic.png\",\n" +
                "            \"createdAt\": \"2022-01-08T06:34:18.598Z\",\n" +
                "            \"lat\": -10.212,\n" +
                "            \"lon\": -16.002\n" +
                "        },{\n" +
                "            \"id\": \"story-FvU4u0Vp2S3PMsFm\",\n" +
                "            \"name\": \"Dimas\",\n" +
                "            \"description\": \"Lorem Ipsum\",\n" +
                "            \"photoUrl\": \"https://story-api.dicoding.dev/images/stories/photos-1641623658595_dummy-pic.png\",\n" +
                "            \"createdAt\": \"2022-01-08T06:34:18.598Z\",\n" +
                "            \"lat\": -10.212,\n" +
                "            \"lon\": -16.002\n" +
                "        },{\n" +
                "            \"id\": \"story-FvU4u0Vp2S3PMsFn\",\n" +
                "            \"name\": \"Dimas\",\n" +
                "            \"description\": \"Lorem Ipsum\",\n" +
                "            \"photoUrl\": \"https://story-api.dicoding.dev/images/stories/photos-1641623658595_dummy-pic.png\",\n" +
                "            \"createdAt\": \"2022-01-08T06:34:18.598Z\",\n" +
                "            \"lat\": -10.212,\n" +
                "            \"lon\": -16.002\n" +
                "        },{\n" +
                "            \"id\": \"story-FvU4u0Vp2S3PMsFo\",\n" +
                "            \"name\": \"Dimas\",\n" +
                "            \"description\": \"Lorem Ipsum\",\n" +
                "            \"photoUrl\": \"https://story-api.dicoding.dev/images/stories/photos-1641623658595_dummy-pic.png\",\n" +
                "            \"createdAt\": \"2022-01-08T06:34:18.598Z\",\n" +
                "            \"lat\": -10.212,\n" +
                "            \"lon\": -16.002\n" +
                "        },{\n" +
                "            \"id\": \"story-FvU4u0Vp2S3PMsFp\",\n" +
                "            \"name\": \"Dimas\",\n" +
                "            \"description\": \"Lorem Ipsum\",\n" +
                "            \"photoUrl\": \"https://story-api.dicoding.dev/images/stories/photos-1641623658595_dummy-pic.png\",\n" +
                "            \"createdAt\": \"2022-01-08T06:34:18.598Z\",\n" +
                "            \"lat\": -10.212,\n" +
                "            \"lon\": -16.002\n" +
                "        },{\n" +
                "            \"id\": \"story-FvU4u0Vp2S3PMsFq\",\n" +
                "            \"name\": \"Dimas\",\n" +
                "            \"description\": \"Lorem Ipsum\",\n" +
                "            \"photoUrl\": \"https://story-api.dicoding.dev/images/stories/photos-1641623658595_dummy-pic.png\",\n" +
                "            \"createdAt\": \"2022-01-08T06:34:18.598Z\",\n" +
                "            \"lat\": -10.212,\n" +
                "            \"lon\": -16.002\n" +
                "        },{\n" +
                "            \"id\": \"story-FvU4u0Vp2S3PMsFr\",\n" +
                "            \"name\": \"Dimas\",\n" +
                "            \"description\": \"Lorem Ipsum\",\n" +
                "            \"photoUrl\": \"https://story-api.dicoding.dev/images/stories/photos-1641623658595_dummy-pic.png\",\n" +
                "            \"createdAt\": \"2022-01-08T06:34:18.598Z\",\n" +
                "            \"lat\": -10.212,\n" +
                "            \"lon\": -16.002\n" +
                "        },{\n" +
                "            \"id\": \"story-FvU4u0Vp2S3PMsFs\",\n" +
                "            \"name\": \"Dimas\",\n" +
                "            \"description\": \"Lorem Ipsum\",\n" +
                "            \"photoUrl\": \"https://story-api.dicoding.dev/images/stories/photos-1641623658595_dummy-pic.png\",\n" +
                "            \"createdAt\": \"2022-01-08T06:34:18.598Z\",\n" +
                "            \"lat\": -10.212,\n" +
                "            \"lon\": -16.002\n" +
                "        },{\n" +
                "            \"id\": \"story-FvU4u0Vp2S3PMsFt\",\n" +
                "            \"name\": \"Dimas\",\n" +
                "            \"description\": \"Lorem Ipsum\",\n" +
                "            \"photoUrl\": \"https://story-api.dicoding.dev/images/stories/photos-1641623658595_dummy-pic.png\",\n" +
                "            \"createdAt\": \"2022-01-08T06:34:18.598Z\",\n" +
                "            \"lat\": -10.212,\n" +
                "            \"lon\": -16.002\n" +
                "        }\n" +
                "    ]\n" +
                "}"
    }

    fun getEmptyStoriesJson():String{
        return "{\n" +
                "    \"error\": false,\n" +
                "    \"message\": \"Stories fetched successfully\",\n" +
                "    \"listStory\": [\n" +
                "        \n" +
                "    ]\n" +
                "}"
    }

    fun getAllStoriesErrorJson():String{
        return "{\n" +
                "    \"error\": true,\n" +
                "    \"message\": \"Missing authentication\"\n" +
                "}"
    }


    fun registerJson(): String {
        return "{\n" +
                "  \"error\": false,\n" +
                "  \"message\": \"User Created\"\n" +
                "}"
    }

    fun registerResponse(json:String): RegisterResponse {
        return Gson().fromJson(json, RegisterResponse::class.java)
    }


    fun registerErrorJson():String{
        return "{\n" +
                "  \"error\": true,\n" +
                "  \"message\": \"input empty\"\n" +
                "}"
    }

    private fun loginJson(): String {
        return "{\n" +
                "    \"error\": false,\n" +
                "    \"message\": \"success\",\n" +
                "    \"loginResult\": {\n" +
                "        \"userId\": \"user-yj5pc_LARC_AgK61\",\n" +
                "        \"name\": \"Arif Faizin\",\n" +
                "        \"token\": \"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJ1c2VyLXlqNXBjX0xBUkNfQWdLNjEiLCJpYXQiOjE2NDE3OTk5NDl9.flEMaQ7zsdYkxuyGbiXjEDXO8kuDTcI__3UjCwt6R_I\"\n" +
                "    }\n" +
                "}"
    }

    fun loginResponse(): LoginResponse {
        val json = loginJson()
        return Gson().fromJson(json, LoginResponse::class.java)
    }

    private fun addStoryJson(): String {
        return "{\n" +
                "    \"error\": false,\n" +
                "    \"message\": \"success\" \n" +
                "}"
    }

    fun addStoryResponse(): FileUploadResponse {
        val json = addStoryJson()
        return Gson().fromJson(json, FileUploadResponse::class.java)
    }
}