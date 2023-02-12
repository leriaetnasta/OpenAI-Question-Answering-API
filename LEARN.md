In this file I explain how I made this project step by step.

Before we start we need to understand the ChatGPT request, expected Input and Output. 
For that we will refer to the examples provided by the OpenAI documentation


Take a look at the Input.

```
{
  "model": "text-davinci-003",
  "prompt": "Say this is a test",
  "max_tokens": 7,
  "temperature": 0,
  "top_p": 1,
  "n": 1,
  "stream": false,
  "logprobs": null,
  "stop": "\n"
}
```

You can see that the post body defines some parameters some of which are optional except for the model parameter.

For this project we will do with just 4 parameters:

_model_ expects a String, we will keep the same model as the example which is _text-davinci-003_ but you can check their overview for more models.
_prompt_ expects a String or an Array, it will contain the question we want to ask ChatGPT.
_temperature_ expects a Number.
_max_tokens_ expects an Integer. 
You can play around with temperature and max_tokens values but will use 4096 and 1 respectively.

Now that we're finished with the Input let's take a look at the Output 

```
{
  "id": "cmpl-uqkvlQyYK7bGYrRHQ0eXlWi7",
  "object": "text_completion",
  "created": 1589478378,
  "model": "text-davinci-003",
  "choices": [
    {
      "text": "\n\nThis is indeed a test",
      "index": 0,
      "logprobs": null,
      "finish_reason": "length"
    }
  ],
  "usage": {
    "prompt_tokens": 5,
    "completion_tokens": 7,
    "total_tokens": 12
  }
}

```
The most interesting property here is "text" which is a String, it will contain the answer to the question sent earlier to the API.

Now we can start to imagine how our java classes will look like.

The Input java class

![Screen Shot 2023-02-12 at 09 41 21](https://user-images.githubusercontent.com/61632665/218301216-b7ee712b-542b-4a47-8a7f-5ca49c468c46.png)

Since Choices in a list of Objects we will create a Choice class

![Screen Shot 2023-02-12 at 09 41 41](https://user-images.githubusercontent.com/61632665/218301231-29565c1e-3dbf-4a26-9f3a-1c3906ca95af.png)


And a Usage record since we don't need a getter

![Screen Shot 2023-02-12 at 09 46 09](https://user-images.githubusercontent.com/61632665/218301393-91bb9781-ea8d-43b2-ae78-4789bd3460cb.png)


The Output java class

![Screen Shot 2023-02-12 at 09 46 30](https://user-images.githubusercontent.com/61632665/218301404-4f32cc8c-e10d-4486-9115-98f55e33a673.png)


You can hardcode the parameters inside the constructor or use the application.properties with the following variables

![Screen Shot 2023-02-12 at 09 46 48](https://user-images.githubusercontent.com/61632665/218301420-591ec857-92c5-4e2e-8bc6-877ab25bf7ae.png)


Generate your OpenAI API Key here

https://platform.openai.com/account/api-keys

Click on Create new secret key

![ae1ip2jjgpots7f3rz8m](https://user-images.githubusercontent.com/61632665/218301509-741b15af-7980-4f68-905d-97447674eaa3.png)



In your Service Java class add the methods that will communicate with the API

To understand the next steps take a look at an Example of a request 

```
curl https://api.openai.com/v1/completions \
  -H 'Content-Type: application/json' \
  -H 'Authorization: Bearer YOUR_API_KEY' \
  -d '{
  "model": "text-davinci-003",
  "prompt": "Say this is a test",
  "max_tokens": 7,
  "temperature": 0
}'

```

This request expects a URL that points to the API
A header that defines the content type as JSON object
An authorization header as a Bearer Token
And the body 
 
So we will build an http client request that will take the parameters defined above and send it to the URI. The answer will then be returned as a Json String.

![Screen Shot 2023-02-12 at 09 47 13](https://user-images.githubusercontent.com/61632665/218301443-9587ec37-ba8b-4aee-aaef-cca18cd70992.png)



Create another method that takes as parameters the prompt which is a String that contains the question.

Inside this method we will create an Input object using the class constructor and pass it the predefined parameters along with the prompt.
Next we will pass the object to the sendChatgptRequest() method as a JSON string and map the response into an Output Object using the ObjectMapper class.
If you want to save the data to a database add the JPA repository Interfaces and save the call and answer objects at this point.

![Screen Shot 2023-02-12 at 09 49 29](https://user-images.githubusercontent.com/61632665/218301523-4ec78ee3-5011-4f68-ab81-cec6212e901f.png)


Inside your Rest Controller class add your Post request 

![Screen Shot 2023-02-12 at 09 49 56](https://user-images.githubusercontent.com/61632665/218301531-feea8177-02f8-499b-acc5-10bb9cab6c18.png)
