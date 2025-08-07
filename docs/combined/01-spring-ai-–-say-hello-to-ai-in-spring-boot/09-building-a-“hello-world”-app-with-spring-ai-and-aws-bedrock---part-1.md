## Setting Up LM Models: Cloud Provider Approach with AWS Bedrock

We've previously explored setting up Language Models (LMs) using the OpenAI platform and local options like Ollama and Docker. Each approach has its own set of advantages and disadvantages.

*   OpenAI offers zero setup, making it easy to get started. However, it requires paying based on consumption.
*   Ollama and Docker require more setup and maintenance but eliminate usage-based costs, only requiring you to pay the server bill.

Organizations choose between these options based on their priorities. Some prioritize ease of setup, while others prioritize cost savings or data privacy.

### Cloud Providers: A Third Approach

Beyond OpenAI and local setups, a third popular approach involves using cloud providers like AWS, Azure, and GCP. These providers offer services that allow you to quickly set up LM models within their cloud infrastructure.

#### Key Differences from OpenAI

The main difference between using OpenAI directly and using cloud providers is **model diversity**.

*   OpenAI locks you into their models.
*   Cloud providers offer a wider range of models from various companies, each specializing in different tasks (e.g., image generation, video generation).

📌 **Example:** While OpenAI excels at communication and charting, other companies offer superior models for image or video generation.

#### Benefits of Using Cloud Providers

1.  **Model Variety:** Access to a diverse range of models tailored to specific use cases.
2.  **Seamless Integration:** If your applications are already deployed on a cloud provider, integrating AI models within the same environment simplifies maintenance and integration.

### AWS Bedrock Demo

We'll now explore the cloud provider approach using AWS Bedrock as an example. You can apply the same principles to Azure and GCP.

#### What is AWS Bedrock?

AWS Bedrock is a service that provides access to foundational models from various companies.

*   You can find information about Amazon Bedrock on its product page.
*   The pricing tab lists the available models from different providers.

📌 **Example:**
    *   Amazon offers its own foundational models like Amazon Titan.
    *   Other providers include Anthropic, Cohere, Meta, Mistral, and Stability AI.

⚠️ **Warning:** You won't find OpenAI models on AWS Bedrock because OpenAI is backed by Microsoft and primarily available through Azure.

#### Equivalent Services on Other Cloud Providers

*   Azure: Azure OpenAI Service
*   GCP: Google Vertex AI

#### Logging into the AWS Console

1.  Log in to the AWS console using your root user email.
2.  You'll need an AWS account with a credit card set up.
    *   If you don't have an account or prefer not to use a credit card, you can skip the setup and watch the demo.
    *   If you have an account, try the demo, as the setup and testing should only cost a few cents.
3.  Select the region closest to you.
    *   📌 **Example:** Hyderabad was selected in the demo.

📝 **Note:** The UI of the AWS console may change over time, but the underlying steps should remain consistent.

#### Accessing Bedrock

1.  Search for "Bedrock" in the AWS console search box.
2.  Open the Bedrock service.
3.  Click on "Model providers" or "Model catalog" to view available models.

📝 **Note:** The available models vary depending on the selected region.

📌 **Example:** In Hyderabad, only Amazon and Anthropic models were supported. Changing the region to Mumbai revealed more options.

#### Exploring the Model Catalog

The model catalog displays models from various providers, including:

*   Anthropic
*   Amazon
*   DeepSeek
*   Hugging Face
*   IBM
*   Meta
*   Mistral

💡 **Tip:** Cloud providers handle the groundwork of providing access to these foundational models, allowing you to focus on selecting the right model for your use case.

#### Requesting Model Access

By default, access to foundational models is disabled. You need to request access before using them.

1.  Click the three dots next to the model you want to use and select "Modify access".
2.  Select the models you want to access.
3.  Click "Request Model Access".
4.  Select all the models you want to access and click "Next".
5.  Click "Submit".

Access is usually granted instantly or within 5-10 minutes.

#### Setting Up Credentials for Spring AI Integration

To enable communication between your Spring AI application and AWS Bedrock, you need to set up credentials.

1.  Go to IAM (Identity and Access Management).
2.  Create a new policy.
    *   Service: Bedrock
    *   Action: "InvokeModel" (under "Invoke Action")
    *   Resource: Initially, use "All resources" (wildcard "*") for the demo, but ⚠️ **Warning:** do not use this in production.  Instead, restrict access to specific models.
    *   Name: "invoke-bedrock-model-policy"
    *   Description: "Policy to invoke bedrock models"
3.  Create a new user.
    *   Name: "spring-ai-demo"
    *   Attach the "invoke-bedrock-model-policy" policy.
4.  Create an access key for the user.
    *   Go to the user's "Security credentials" tab.
    *   Click "Create access key".
    *   Select "Local code" as the use case. ⚠️ **Warning:** Do not use this option for production environments.
    *   Copy the access key and secret access key to a secure location.

```text
Access Key ID: YOUR_ACCESS_KEY_ID
Secret Access Key: YOUR_SECRET_ACCESS_KEY
```

You now have the necessary credentials to configure your Spring AI application to communicate with AWS Bedrock. We will continue this discussion in the next lecture.
