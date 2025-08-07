## Introduction to Large Language Models (LLMs)

Let's dive into the world of Large Language Models (LLMs)! 🤖

LLMs are essentially text experts. You can ask them almost anything, and they'll usually provide a correct answer.

These special AI systems are trained on massive amounts of text from books, websites, articles, and more. They excel at understanding and generating human-like text. ✍️

The term "large" comes from the fact that they are trained on billions of words and have billions of parameters. We'll explore what these parameters are in future lectures.

### Unsupervised Learning

During training, LLMs use **unsupervised learning**. This means no AI engineer needs to manually prepare or label the input data. This is a significant advantage over traditional machine learning and deep learning methods, which often require supervised training.

📌 **Example:** In traditional machine learning, if you want to train a system to recognize the sentiment of a text, an engineer would need to provide a lot of training data, labeling each sentence with its sentiment (e.g., "I am happy with the service" labeled as "happy" or "positive").

LLMs, however, learn directly from raw, unlabeled text data. They learn patterns from billions of words and phrases without manual intervention. 🤯

### Why LLMs Need So Much Power

LLMs require significant computational power because, during training, they constantly try to predict the next word in a sentence. To do this, they perform complex mathematical calculations to determine probabilities. 🧮

This process requires massive computational power, especially during training. Therefore, LLMs rely on **GPUs** (Graphics Processing Units) rather than CPUs (Central Processing Units). GPUs are designed for fast parallel processing, making them ideal for these types of calculations. 🚀

### The Scale of LLMs

To give you an idea of the scale of these models:

*   The GPT-4 LLM model, used by ChatGPT, is trained on 1 to 2 trillion tokens, which is roughly 300 to 500 billion words. 🤯
*   This is equivalent to reading over 1 million books or nearly the entire internet's text content.
*   GPT-4 may have used 10,000 to 25,000 GPUs provided by Nvidia.

The capacity of LLMs is increasing so rapidly that we may soon face a shortage of publicly available internet data to train them. ⚠️

### The Cost of Training

Building and training an LLM is a costly endeavor:

*   It typically costs between $50 million and $100 million. 💰
*   Training involves tens of thousands of petaflop days of compute.
*   GPT-4 likely took several weeks to a few months to complete training.

To put this into perspective, it would take a human 114 years to read the same amount of data as GPT-4, working 24/7 without breaks. Even then, humans can't remember everything they read, while LLMs can retain all the patterns they identify during training. 🧠

### GPUs vs. CPUs

Why are GPUs used instead of CPUs?

*   CPUs have a limited number of powerful cores (typically 4 to 16) optimized for general-purpose tasks.
*   GPUs have thousands of smaller cores, perfect for performing many simple mathematical operations simultaneously (parallel processing). 💻

This makes GPUs ideal for the computationally intensive tasks involved in training LLMs. However, GPUs are costly and require specialized expertise to build.

Currently, only a few companies can build GPUs, and training LLMs requires at least 10,000 GPUs, making it inaccessible for most individuals or small companies. Hopefully, in the future, we'll be able to run LLMs locally using CPUs, or GPUs will become more affordable. 🙏

### How LLMs Learn Patterns

LLMs learn patterns in a way that's similar to how babies learn to talk.

Babies listen to tons of conversations and gradually notice patterns.

📌 **Example:** A baby might hear "Good morning" repeatedly and learn that "morning" often follows "good." Or they might hear "How are you?" frequently and learn that "I'm fine" is a common response.

Similarly, LLMs learn by reading millions of books, websites, and conversations.

📌 **Example:** An LLM might see the sentence "The cat sat on the..." thousands of times and start to notice what words commonly follow it (e.g., "mat," "chair," "roof").

During training, LLMs constantly play a guessing game with themselves, predicting the next word in a sequence. When their guess is wrong, they update their learning patterns. 🎯

📌 **Example:** An LLM might read the sentence "I'm going to the store to buy some..." Based on its training data, it might predict "milk," "bread," or "apples." If it then encounters the word "groceries," it will realize that "groceries" is also a valid answer in the context of a store and update its pattern accordingly.

By the end of training, LLMs build a giant web of interconnected ideas in their "brain." For example, the word "store" might be closely connected to words like "shopping," "money," and "items," while the word "rain" might be connected to words like "umbrella," "wet," and "clouds." 🌧️

This is a simplified explanation, and we'll delve into more details about how LLMs identify these patterns in future lectures.

📝 **Note:** Hopefully, this gives you a good introduction to LLMs!
