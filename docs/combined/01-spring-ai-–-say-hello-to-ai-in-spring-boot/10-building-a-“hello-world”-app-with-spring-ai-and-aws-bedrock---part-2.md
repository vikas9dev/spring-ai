## Integrating Spring AI with AWS Bedrock

This section details the steps to integrate a Spring AI project with AWS Bedrock. We'll start with an existing Spring AI project and modify it to use Bedrock instead of OpenAI.

First, we'll modify the `pom.xml` file.

Instead of an OpenAI starter project, we'll use the Bedrock connector.

```xml
<dependency>
    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-bedrock</artifactId>
    <version>YOUR_SPRING_AI_VERSION</version>
</dependency>
```

After adding the dependency, sync the Maven changes.

Next, we need to configure the `application.properties` file.

1.  Remove all properties related to OpenAI.
2.  Add the properties related to AWS Bedrock.

A common question is: How do I know which properties to configure for a specific model?

The answer is the official Spring AI documentation. 💡 **Tip:** Always refer to the official documentation for the most up-to-date information.

The documentation provides details on dependencies and properties for various models, including:

*   Amazon Bedrock
*   Anthropic
*   Azure OpenAI
*   DeepInfra
*   Docker Model Runner
*   Google Vertex AI
*   Grok
*   Hugging Face
*   Mistral
*   Minimax
*   Moonshot
*   Nvidia
*   Llama
*   Perplexity
*   OpenAI

For Amazon Bedrock, the following properties need to be configured:

*   `spring.ai.bedrock.aws.region`: The AWS region.
*   `spring.ai.bedrock.aws.access-key`: The AWS access key.
*   `spring.ai.bedrock.aws.secret-key`: The AWS secret key.
*   `spring.ai.bedrock.model`: The Bedrock model ID.

📌 **Example:**

```properties
spring.ai.bedrock.aws.region=ap-south-1
spring.ai.bedrock.aws.access-key=YOUR_ACCESS_KEY
spring.ai.bedrock.aws.secret-key=YOUR_SECRET_KEY
spring.ai.bedrock.model=anthropic.claude-v2
```

📝 **Note:** For the AWS region, you can find the code name in the AWS console.

⚠️ **Warning:**  Do not hardcode sensitive data like access keys and secret keys in your `application.properties` file in real applications. Use environment variables or other secure methods to inject this data.

The `spring.ai.model.chat.enabled` property is enabled by default. You can keep it for reference.

The `spring.ai.bedrock.model` property specifies the model to use from Bedrock.

To find the model ID, navigate to the AWS Bedrock service in the AWS console.

1.  Go to the Model Catalog.
2.  Select the desired model (e.g., Anthropic Claude).
3.  Copy the model ID from the Inference profile.

After configuring the properties, build the project.

Rename the main class to `BedrockApplication` and update the file name accordingly.

During initial testing, the application might fail to start due to a missing region configuration.

It seems there is a bug where the Spring framework expects the region property to be set both in `application.properties` and as an environment variable.

To set the environment variable:

1.  Go to Edit Configurations.
2.  Modify options and select Environment variables.
3.  Add `AWS_REGION` with the region value (e.g., `ap-south-1`).

The environment variable name should be `AWS_REGION`.

After setting the environment variable, restart the application.

If the application still fails to start, double-check the environment variable name. The framework might be expecting `AWS_REGION`.

Once the application starts successfully, test the APIs using Postman.

If you encounter an exception stating that the model ID is not supported for on-demand invocation, it means you need to use the model ID from the inference profile instead of the model catalog.

1.  In the AWS console, navigate to Bedrock.
2.  Go to Cross-region inference under Infer.
3.  Copy the model ID from the Inference profile ID column.

Update the `spring.ai.bedrock.model` property with the new model ID and restart the application.

After these steps, the integration between Spring AI and Bedrock should be working. You should receive successful responses from the Bedrock model.

This setup allows you to leverage the power of AWS Bedrock's cloud LM models within your Spring AI application.

It's possible to combine multiple vendor models within a Spring AI application. This will be covered in future lectures.

All the details discussed around AWS Bedrock are documented in the provided slides. Refer to these slides for a quick refresher on Spring AI skills.
