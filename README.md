# Openai-api
This projects saves questions sent and answers retrieved from chatgpt to a csv file.




The OpenAI expected Input

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

For this project I only used 4 parameters:

_model_ expects a String, we will keep the same model as the example which is _text-davinci-003_ but you can check their overview for more models.

_prompt_ expects a String or an Array, it will contain the question we want to ask ChatGPT.

_temperature_ expects a Number.

_max_tokens_ expects an Integer. 

You can play around with temperature and max_tokens values but will use 4096 and 1 respectively.

The expected Output 

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



You can hardcode the parameters inside the constructor or use the application.properties with the necessary variables.

To generate your OpenAI API Key visite this page

https://platform.openai.com/account/api-keys

Click on Create new secret key

![API Key](https://dev-to-uploads.s3.amazonaws.com/uploads/articles/ae1ip2jjgpots7f3rz8m.png)

In the Service Java class I added the methods that will communicate with the API

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

This request expects a URL that points to the API,
A header that defines the content type as JSON object,
An authorization header as a Bearer Token
And the body 
 
So I built an http client request that will take the parameters defined above and send it to the URI. The answer will then be returned as a Json String.

