## Testing the New REST API

Let's dive into testing our new REST API! 🚀

I've set a breakpoint at line 42 in Postman to observe the data flow.

First, I'll ask a question about "Mars," even though the loaded data doesn't contain any information about it. Ideally, the API should respond with "I don't know."

I'll use the username "modern11" and click "Send."

The breakpoint hits, and let's examine the data:

*   We have three sentences: one about the Sahara, one about Japan, and one about Earth.
*   These are considered the most relevant sentences to the query about "Mars."

The system prompt will receive these sentences. Since they don't contain information about Mars, the LLM should respond with "I don't know."

And it does! ✅

Next, I'll ask about "Redis." There *is* a statement about Redis in the random data loader.

Let's see what happens. I'm clicking the "Send" button.

The context includes sentences about Redis, REST APIs, and PostgreSQL.

Again, it's doing a good job! 👍

After releasing the breakpoint, the response includes: "Redis is an in-memory data store used for caching."

Now, I'll ask about "MySQL," which is *not* mentioned in the data.

The context includes information about PostgreSQL, Redis, and Java.

Releasing the breakpoint, the response is "I don't know," which is the expected behavior. ✅

Let's try some other scenarios.

There's a statement about "walking" in the data loader: "Walking 30 minutes in a day improves cardiovascular health."

I'll ask: "What happens if I walk daily?" This is intentionally different from the original statement.

The context includes the walking-related sentence, a sentence about setting daily goals, and a sentence about reading daily. All are related to lifestyle.

Are you seeing the power of the vector store? 🤯 It searches based on *meaning*, not just content.

The response includes the sentence: "Walking 30 minutes a day improves cardiovascular health." We're getting the same sentence defined in the loader class.

This is due to the instructions in the system prompt.

💡 **Tip:** If you update the system prompt to instruct the LLM to use the information and provide more descriptive answers, you'll get more detailed responses. I'll leave that as an exercise for you.

Next question: "What do you know about Niagara Falls?"

There's a statement about Niagara Falls in the loader class.

The response: "Niagara Falls is located between Canada and the US."

Last question: "Tell me about investments."

We have several sentences about investments.

The context includes sentences about diversifying investments, compound interest, and mutual funds.

Releasing the breakpoint, I expected a better response, but I got "I don't know." 😕

Maybe I need to fine-tune the message.

I'll add a question mark: "Tell me about investments?"

Releasing the breakpoint...

This time, we got a meaningful response! 🎉

This highlights one of the problems with LLM responses: they are not always predictable. However, it's generally better for them to respond with "I don't know" than to generate incorrect information.

Hopefully, you're clear on the RAG flow we've implemented so far.

Before closing, let me share an important piece of information regarding your `pom.xml` file.

⚠️ **Warning:** Please make sure you are deleting the dev tools related dependency.

The reason is that whenever you make a change in your project and do a build, it will restart the application behind the scenes. This causes the random data loader to be recreated, adding duplicate data to the vector store.

To avoid this, I recommend removing the dev tools dependency.

Alternatively, you could add logic to the class to check if data already exists and, if so, prevent reloading it.

So far, we've used a simple example. In the next lecture, we'll explore a more realistic use case.

📝 **Note:** Example of devtools dependency in pom.xml:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
    <optional>true</optional>
</dependency>
```
