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


The Input java class
<script src="https://gist.github.com/leriaetnasta/557406831582be1bd144e6f927393c2a.js"></script>
Since Choices in a list of Objects we will create a Choice class
<script src="https://gist.github.com/leriaetnasta/6b3af8f06bcf27daaa5f3f7bac7734f3"></script>
And a Usage record since we don't need a getter
<script src="https://gist.github.com/leriaetnasta/1980809b9a0f505b81ddfe5d4617404d"></script>
The Output java class
<script src="https://gist.github.com/leriaetnasta/ce9622666b3d0ae8721227a02488fcd1"></script>


You can hardcode the parameters inside the constructor or use the application.properties with the following variables

<script src="https://gist.github.com/leriaetnasta/7e0e0168e57270effff473be06655e39.js"></script>

To generate your OpenAI API Key visite this page

{% embed https://platform.openai.com/account/api-keys %}

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

This request expects a URL that points to the API
A header that defines the content type as JSON object
An authorization header as a Bearer Token
And the body 
 
So I built an http client request that will take the parameters defined above and send it to the URI. The answer will then be returned as a Json String.

<script src="https://gist.github.com/leriaetnasta/900ba4014c42c3a30e8163fc8883d740"></script>

The next method takes as parameters the prompt which is a String that contains the question.

Inside this method I created an Input object using the class constructor and passed it the predefined parameters along with the prompt.
Next I passed the object to the sendChatgptRequest() method as a JSON string and map the response into an Output Object using the ObjectMapper class.

<script src="https://gist.github.com/leriaetnasta/9431f311a2e17c9bc9b2b261c3db377d"></script>

Inside the Rest Controller class I added a Post request 

<script src="https://gist.github.com/leriaetnasta/733a9f3525e7afafbc024ccb6e030287"></script>
