# 03 Foundations Of Generative Ai And Llms


Sections-
1. [Understanding Generative AI and Language Models](#1-understanding-generative-ai-and-language-models)
2. [Understanding the Generative AI Family Tree 🌳](#2-understanding-the-generative-ai-family-tree-)
3. [AI Humor: A Lighthearted Look at Artificial Intelligence](#3-ai-humor-a-lighthearted-look-at-artificial-intelligence)
4. [Generative AI Overview](#4-generative-ai-overview)
5. [Introduction to Large Language Models (LLMs)](#5-introduction-to-large-language-models-llms)
6. [How Large Language Models (LLMs) Work: Guessing the Next Word](#6-how-large-language-models-llms-work-guessing-the-next-word)
7. [Understanding Tokens in Large Language Models](#7-understanding-tokens-in-large-language-models)
8. [Model Vocabulary in Language Models](#8-model-vocabulary-in-language-models)
9. [Understanding Word Embeddings in AI](#9-understanding-word-embeddings-in-ai)
10. [Understanding Embeddings and Vectors in Language Models](#10-understanding-embeddings-and-vectors-in-language-models)
11. [Understanding Token Relationships and Embeddings in Language Models](#11-understanding-token-relationships-and-embeddings-in-language-models)
12. [Understanding Positional Encoding in Transformer Models](#12-understanding-positional-encoding-in-transformer-models)
13. [Understanding the Attention Layer in Language Models](#13-understanding-the-attention-layer-in-language-models)


---

## 1. Understanding Generative AI and Language Models

This section provides a foundational understanding of Generative AI, Large Language Models (LLMs), and related concepts. It's designed to equip you with the necessary knowledge to effectively utilize Spring AI in more advanced use cases.

If you're already familiar with AI and LLM concepts, feel free to skip this section. Review the provided slides; if the concepts are clear, proceed to the next section. Otherwise, I highly recommend going through this material.

📝 **Note:** This section focuses on theory and does not cover Spring AI directly.

The goal is to ensure that as a Spring AI developer, you possess a basic understanding of:

*   What Generative AI is.
*   What LLMs are.
*   How LLMs work internally.

For instance, we'll explore concepts like embeddings, vector stores, and vector databases. A solid grasp of LLMs and Generative AI is essential for understanding these topics.

Let's dive into Generative AI!

### The Journey to Generative AI

Generative AI isn't a recent overnight phenomenon. It's the result of a long history of technological advancements.

Think of Generative AI as a super-smart artist or writer that uses computers to create new things: stories, images, videos, ideas, and responses to our questions. 🤖 It's like a robot that can draw a picture or write a poem on its own. 🎨✍️

Generative AI is part of a larger family of technologies that help computers think and learn. This family includes:

*   Artificial Intelligence (AI)
*   Machine Learning (ML)
*   Deep Learning (DL)
*   Neural Networks
*   Natural Language Processing (NLP)
*   Large Language Models (LLMs)

Let's look at how these technologies evolved:

1.  **1950s:** Artificial Intelligence (AI) emerged as a computer science field.
2.  **1990s:** Machine Learning (ML) developed as a subset of AI.
3.  **2010s:** Deep Learning (DL) gained prominence.
4.  **2020s:** Generative AI (GenAI) came into the picture.

Here's another way to visualize this family tree:

*   **Artificial Intelligence (AI):** The "grandparent" or "super parent" 👵👴
*   **Machine Learning (ML):** A branch of AI. 🌳
*   **Deep Learning (DL):** A branch of ML. 🌳
*   **Generative AI (GenAI):** A branch of DL. 🌳
    *   **Large Language Models (LLMs):** Focus on generating text. ✍️
    *   **Image Generation Models:** Create images. 🖼️
    *   **Video Generation Models:** Create videos. 🎬
    *   **Code Generation Models:** Create code. 💻

Parallel to this evolution, other major fields have also developed:

*   **Data Science:** Combines mathematics, statistics, programming, and domain knowledge to extract meaningful insights from data. 📊 It's largely used to make predictions based on historical data.
*   **Natural Language Processing (NLP):** Enables us to interact with these technologies using natural language like English. 🗣️ When we ask questions to Generative AI, NLP is used to understand our instructions.

I hope this provides a basic understanding of the family tree of Generative AI. In the next lecture, we'll explore these technologies in more detail.

---

## 2. Understanding the Generative AI Family Tree 🌳

This section provides a breakdown of the core technologies within the generative AI landscape.

### Artificial Intelligence (AI) 🧠

*   AI emerged around the 1950s with the goal of creating computers that behave like humans.
*   The aim is to enable machines to think, learn, solve problems, and make decisions like humans.
*   Essentially, it's about giving machines a "brain" capable of reasoning and decision-making.
*   📌 **Example:** Siri, Google Assistant, and self-driving cars are all products evolved from AI research.
*   AI is the overarching term for all smart computer systems, sitting at the top of the family tree.
*   📝 **Note:** It's best to use the generic term "artificial intelligence" when explaining these concepts to people unfamiliar with the field to avoid confusion with jargon like "generative AI," "deep learning," and "machine learning."

### Machine Learning (ML) 🤖

*   ML is a subset of AI.
*   It involves teaching computers to learn from examples rather than providing strict rules.
*   Instead of giving specific instructions, ML allows computers to learn patterns from data.
*   📌 **Example:** Teaching a computer to recognize cats by showing it thousands of cat pictures.
*   Real-world applications include:
    *   Email spam detection 📧
    *   Online shopping recommendations 🛍️
    *   Voice recognition 🗣️
*   In simple terms, machine learning is how computers learn from experience, much like humans do.
*   💡 **Tip:** The word "learning" in "machine learning" highlights the core concept of teaching machines to learn on their own.
*   However, ML can only solve small use cases.

### Deep Learning (DL) 🧠➡️🧠

*   Deep learning is a more advanced form of machine learning that mimics the human brain using neural networks.
*   It uses artificial neural networks with multiple layers, hence the term "deep."
*   Each layer learns different features, such as edges, shapes, and complete objects.
*   📌 **Example:**
    *   Image recognition (like Google Photos automatically creating albums based on recognized people) 🖼️
    *   Language translation 🌐
    *   Self-driving cars 🚗
*   Deep learning has enabled significant progress in solving complex problems, particularly in fields like medicine.
*   Deep learning works in layers:
    1.  Layer 1: Recognizes lines and edges.
    2.  Layer 2: Combines lines to see shapes.
    3.  Layer 3: Combines shapes to recognize objects.
*   Similarly, for text:
    *   Lower layers learn grammar.
    *   Middle layers learn word relationships (📌 **Example:** understanding the different meanings of "bank").
    *   Higher layers learn meaning and intent, including sentiment.
*   The building blocks of deep learning are neural networks, designed to work like brain cells.
*   Each artificial neuron receives information, processes it, and passes it on to the next layer.
*   Neural networks are interconnected, with each connection having different strengths.
*   They excel at finding patterns that humans might miss and improve with more data and practice.

### Natural Language Processing (NLP) 🗣️

*   NLP is a field of AI that helps machines understand and work with human language (reading, writing, and talking).
*   NLP enables communication with AI products like Siri, Google Assistant, and ChatGPT.

### Generative AI ✨

*   Generative AI is the latest trend in AI.
*   Unlike deep learning, which primarily analyzes existing data, generative AI can create entirely new content.
*   It learns from many examples (drawings, songs, stories) and uses this information to create something original.
*   Generative AI is a broad category of creative AI.
*   Sub-technologies include:
    *   Large Language Models (LLMs) for text generation ✍️
    *   Image generators like DALL-E for image creation 🖼️
    *   Models for music generation 🎵, video generation 🎬, and code generation 💻.

### Analogy: The Robot Chef 🧑‍🍳

To remember the roles of these technologies, imagine building a robot chef:

*   **AI:** Gives the robot the ability to cook.
*   **Machine Learning:** Teaches the robot recipes by watching cooking videos or reading cookbooks.
*   **Deep Learning:** Uses a complex brain-like system to perfect its cooking. Neural networks act like the wiring in the robot's brain.
*   **NLP:** Allows the robot to understand spoken instructions (📌 **Example:** "Make pasta").
*   **Generative AI & LLMs:** Enables the robot to invent new recipes and write a cookbook.

### Final Thoughts 🤔

*   The goal is to provide a brief introduction to these technologies.
*   ⚠️ **Warning:** No single person knows everything about all these technologies.
*   Just like in software development, there are specialized roles within AI (NLP experts, deep learning experts, etc.).

---

## 3. AI Humor: A Lighthearted Look at Artificial Intelligence

This section provides a humorous perspective on artificial intelligence and its impact on developers and society. It uses images and memes to convey important messages about the responsible use of AI tools.

The common perception of AI is often just "the tip of the iceberg," while the reality involves complex frameworks, technologies, programming languages, and algorithms.

📌 **Example:** Think of AI as an iceberg. What you see is only a small part of what makes it work.

Do you know who the first victim of AI is? Tom from Tom and Jerry! 😹

Many developers believe they are safe without using emerging GenAI tools, but this is a misconception.

The reality is that developers who don't adopt AI-assisted workflows risk being replaced by those who do.

Before GenAI, developers focused on Web3. Now, everyone is upgrading to become AI developers. 🚀

⚠️ **Warning:** Blindly relying on GenAI tools like ChatGPT, Copilot, or Cursor can lead to problems.

While GenAI can generate code quickly, debugging complex enterprise applications can become a significant challenge if you don't understand the underlying code.

Imagine generating code in 5 minutes, but then spending 24 hours debugging it! ⏳

As GenAI developers, we're tempted to use these tools for everything.

💡 **Tip:** Upskill yourself in programming languages and frameworks through courses or documentation.

Once you have a solid understanding, you can leverage GenAI more effectively. This allows you to judge the accuracy of AI recommendations.

Without a strong foundation, you might incorrectly assume that all GenAI suggestions are correct.

This tweet humorously highlights the limitations of GenAI models:

GPT-5 builds websites perfectly.
GPT-6 builds and runs companies.
GPT-7 passes the Turing test.
GPT-8 overthrows governments.
GPT-9 fails to understand zero. 🤷

In enterprise applications, developers deal with client meetings, Slack messages, and Jira tickets. GenAI may struggle with this context.

💡 **Tip:** Inject relevant context into GenAI to help it write better code.

Always use AI tools as a booster, not as your brain. 🧠

We'll likely see non-programmers building websites and products with GenAI. However, when the system breaks, a senior developer will be needed to debug and fix the issues.

This image presents a humorous (and hopefully unrealistic) scenario:

Doctors using ChatGPT to complete assignments and homework. 🩺

This could be concerning, as relying solely on AI without real knowledge of patient care could lead to mistakes.

The final meme illustrates a potential (dystopian) AI lifecycle:

1.  Humanity builds AI.
2.  AI perfects itself.
3.  AI enslaves humanity.
4.  A solar flare disables AI.
5.  Humanity worships the sun. ☀️

Hopefully, this won't happen in real life!

The key takeaway is to use AI tools as assistants, but not to rely on them completely or allow them to take over. 🤝

---

## 4. Generative AI Overview

Generative AI encompasses various types of models, each with its own strengths and expertise. Some models excel at generating text, while others are proficient in creating images, videos, or code.

Broadly, there are two main types of models:

*   Large Language Models (LLMs)
*   Diffusion Models

### Large Language Models (LLMs)

These models are primarily used for text generation.

📌 **Example:** ChatGPT and Llama.

LLMs are widely used in the industry for tasks like code generation and answering questions.

### Diffusion Models

These models are capable of generating video, image, and audio content.

📌 **Example:** Dall-E 2, Midjourney, and Sora.

From the next lecture, we will focus on LLMs because they are extensively used in enterprise applications to solve business problems. While some applications may utilize diffusion models for image and video generation, the majority of this course will cover leveraging LLMs. We will explore examples of image, video, and audio generation towards the end of the course.

### How Diffusion Models Generate Images

Let's briefly explore how diffusion models generate images, videos, or other data. Diffusion models use a specific technique:

1.  **Training:** The models are trained on a large dataset of images.
    📌 **Example:** A dog image.
2.  **Adding Noise:** During training, the model progressively adds noise (pixel data) to the original image.
3.  **Iteration:** After multiple iterations of adding noise, the original image transforms into a completely noisy image (similar to static on a television).
4.  **Learning:** By analyzing numerous images, the diffusion models learn how to construct new images.
5.  **Reverse Process (Generation):** To generate a sample image based on instructions, the model performs the reverse process.
    *   It starts with a completely noisy image.
    *   The noise is gradually decreased.
    *   The pixel data is arranged in such a way that the desired image (e.g., a dog) begins to appear.
6.  **Final Image:** After multiple iterations of denoising, a perfect image is generated by the diffusion model.

I hope you now have a basic understanding of how diffusion models generate images. This is the current technique used by these models, but advancements are continuously being made.

---

## 5. Introduction to Large Language Models (LLMs)

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

---

## 6. How Large Language Models (LLMs) Work: Guessing the Next Word

At its core, a large language model (LLM) is designed to guess the next word in a sequence. This is the primary function an LLM performs behind the scenes. Think of it as a highly advanced autocomplete system.

Given an input sentence or word, the LLM predicts the next word based on the context.

📌 **Example:** If the input is "once upon a," the LLM might guess "time" to form the meaningful phrase "once upon a time." During training, the model has likely encountered similar sentences and learned the patterns.

### From Single Words to Full Responses

You might wonder: if LLMs only guess the next word, how do tools like ChatGPT provide full-length responses?

The answer is that applications like ChatGPT are **LM wrappers**. They are built by adding extra logic on top of the core LLM.

These wrappers make LLMs user-friendly by:

*   Generating full sentences.
*   Handling conversations.
*   Providing an interface for end-user interaction.

Analogy: Think of the LLM as an engine and the wrapper (like ChatGPT) as a car or vehicle.

### "Eating Its Own Output": Generating Full Responses

LM wrappers use a technique called "eating its own output" to generate complete responses from the underlying LLM. Here's how it works:

1.  **User Input:** You ask ChatGPT a question: "What is the capital of India?"
2.  **Wrapper Processing:** The wrapper (ChatGPT) modifies the question for the LLM. It might prefix the question with "user:" to indicate a user query and add a special character at the end to signal the end of the prompt.
3.  **LLM Prediction:** The LLM, based on its training, understands the question and generates the first word of the answer: "New Delhi."
4.  **Iterative Process:** The wrapper feeds "New Delhi" back to the LLM to generate the next word. This time, the LLM might predict "is."
5.  **Building the Sentence:** This process continues iteratively. The LLM receives the growing sentence ("New Delhi is") and predicts the next word ("the"), and so on.
6.  **End of Text Signal:** Eventually, the LLM generates the complete answer: "New Delhi is the capital of India." It also generates a special "end of text" token.
7.  **Wrapper Termination:** The wrapper recognizes the "end of text" token and stops feeding the input back to the LLM, concluding the response.

### LM Wrappers in the Industry

Many LM wrappers exist, including:

*   Google Gemini
*   ChatGPT
*   Claude (Anthropic)

These wrappers provide the conversational feel that users experience.

On the ChatGPT website, the dropdown menu allows you to select different underlying LM models. The ChatGPT wrapper then handles the interaction with the chosen model.

### The "Attention is All You Need" Paper

💡 **Tip:** The development of LLMs was significantly influenced by the white paper "Attention is All You Need," published in 2017 by Google researchers.

This paper introduced the **Transformer** architecture, a new neural network design.

*   The Transformer was initially used for machine translation tasks.
*   It demonstrated superior quality, parallelization, and training efficiency.

While Google published the paper, OpenAI was the first to release an LLM using the Transformer architecture with ChatGPT.

Initially, Google's LLM wrapper, Google Bard, was not as successful as ChatGPT. However, many vendors, including Microsoft and Twitter, began building LM wrappers. Google has since improved its offering with Google Gemini.

The "Attention is All You Need" paper can be accessed via a PDF link.

The paper details how the Transformer model works, using concepts like:

*   Embedding
*   Positional Encoding
*   Attention Layers

📝 **Note:** As a developer, you don't need to understand every detail of these models, but a basic understanding is beneficial. This series aims to provide that foundational knowledge.

---

## 7. Understanding Tokens in Large Language Models

The basic unit of a large language model is a **token**. Let's explore what tokens are and why they are essential in LLMs.

Previously, it was mentioned that the core job of an LLM is to guess the next word. However, that's not entirely accurate. LLMs actually predict the next **token**.

In simple terms, a token can be thought of as a word. However, the meaning of "token" is slightly different from the traditional definition of an English word.

### Why Tokens?

Large language models are artificial intelligence systems, and computers. They can't directly understand human languages like English. Computers only understand numbers. Therefore, everything we input into an LLM must be converted into numbers, which are **tokens**. 🔢

Similarly, LLMs produce tokens as output. These tokens (numbers) need to be converted back into normal English words. This conversion is handled by the LLM wrapper (e.g., ChatGPT).

*   Input: English words are converted into tokens.
*   Output: Tokens generated by the LLM are converted back into English words.

### What is a Token?

A token can be:

*   A character
*   A word
*   A part of a word

The value of a token depends on the specific LLM model.

📌 **Example:**

One LLM might treat "dog" as a single token, while another might treat it as two tokens: "d" and "og".

Most commonly used English words are typically treated as single tokens. Additionally, suffixes and prefixes like "-ish" and "-ing," as well as single characters (A, B, 1), and special keywords (e.g., end of text, end of prompt), are also treated as tokens.

📝 **Note:** Tokens are not exactly words; they are the pieces models use to build words.

### Tokenization Example: "playfully"

Consider the word "playfully." LLMs might split this word into multiple tokens:

1.  "play" (token one)
2.  "ful" (token two)
3.  "ly" (token three)

This allows LLMs to understand and generate even new or misspelled words by combining known tokens. This is why ChatGPT can often understand questions with spelling mistakes. 💡 **Tip:** LLMs are flexible with new words, typos, or slang because they break them down into known tokens.

An LLM might already have a token for "play." Many English words use "ful" or "-ish" as suffixes or prefixes, so these will also have corresponding tokens. This is how the process works behind the scenes.

### Token IDs and Vectors

When you provide text to ChatGPT, each word or letter is converted into a token, and each token is assigned a unique **token ID**. Each of these token IDs has a specific meaning. From these token IDs, vector numbers are built by the LLM.

Don't worry, we'll discuss the concepts of token IDs and vectors in more detail soon.

### Demo: Tokenizer

Let's look at a demo using OpenAI's Platform Tokenizer to see how English words are converted into tokens.

If you type "play foolish" into the tokenizer, it's converted into three tokens: "play," "ful," and "ish." Each token is highlighted in a different color and has a unique token ID.

📌 **Example:**

For "play," the token ID is 2003. This token ID is static. If you type "play well," the token ID for "play" will still be 2003. However, if you capitalize the first letter ("Play well"), the token ID changes (e.g., to 10245).

Even though it seems trivial, computers need this distinction to differentiate between uppercase and lowercase letters.

📌 **Example:**

Typing "Happy for you" results in three tokens: "Happy," " for," and " you." The token ID for "Happy" is 37408.

If you type "Happy birthday," "birthday" is treated as a single token along with the space. The token ID for "Happy" remains 37408.

📌 **Example:**

Typing "You seems happy" results in different token IDs because " happy" (with a leading space) is considered a single token. The token ID for " happy" is 27213.

Adding a space can significantly change the token ID.

📌 **Example:**

Typing " Happy birthday" (with leading spaces) results in " Happy" and " birthday" as separate tokens.

If you remove the leading space, the token ID for "Happy" changes back to 37408.

Hopefully, you now have a clearer understanding of how tokens are formed from input text.

The next lecture will cover the meaning of these token IDs and how they are used inside LLM models.

---

## 8. Model Vocabulary in Language Models

During training, each Language Model (LM) constructs its own **model vocabulary**. LMs ingest billions of words and use smart algorithms to identify the most frequently encountered words. These words become **tokens**.

📌 **Example:** "dog," "happy," "king," "queen," and "football" are common tokens.

Most frequently used words in everyday communication often become single tokens. Less common words are typically split into multiple tokens.

The LM creates a table-like structure to perform lookups during the tokenization process.

📌 **Example:**

| Token ID | Word  |
| :------- | :---- |
| 0        | high  |
| 1        | r     |
| 2        | u     |

Recent LMs have vocabulary sizes around 100,000 tokens.  Future models may reach 1 million tokens. Currently, powerful models like GPT-4 have approximately 100K tokens, while less powerful models like BERT have around 30,000 tokens.

The larger the vocabulary size, the better the LM's performance.

During text generation, each token is assigned a unique ID.

📌 **Example:**

*   "king" = Token ID 1342
*   "queen" = Token ID 39
*   "football" = Token ID 8121

When an end-user inputs "king," the LM receives token ID 1342. The LM recognizes this ID and understands the user is referring to a male ruler, royalty, wealth, etc.

Each token ID has an embedded meaning.  The token ID points to a vector number containing detailed information about the word.

The model vocabulary starts empty and is populated during training based on the training data and the vocabulary size specified by AI engineers. The LLM calculates and populates the vocabulary, token IDs, and embedded meanings using vectors.

### Handling Unknown Tokens

What happens if a token is not found in the vocabulary?

📌 **Example:** The model's vocabulary includes "apple," "phone," and "table," but the input is "tabletop."

Since "tabletop" is uncommon, it's not in the vocabulary. The model uses subword tokenizing algorithms to break down the unknown word into known smaller pieces.

"Tabletop" might be split into "table" and "top," each with its own token ID. The LLM then understands the embedded meaning using these token IDs.

If "table" and "top" are also absent, the algorithm further splits them into "tab," "le," "to," and "p." This continues until corresponding tokens are found in the vocabulary.

💡 **Tip:** If a word is unknown, the tokenizer splits it into the smallest number of known subword tokens.

Even if the vocabulary lacks "multi cloud ready," it might tokenize it as "multi," "cloud," and "ready."

📝 **Note:** Model vocabularies vary between LMs. The LM decides the vocabulary during training. AI engineers control the model vocabulary size, but the LM determines tokens, IDs, and embedded meanings.

### Why Tokens Instead of Words or Characters?

Why don't LMs use all known words or just characters?

Language is complex and unpredictable. It's impossible to predict all word combinations. Users may use rare words or misspellings.

📌 **Example:** "unconstitutional" or "gud" instead of "good."

New words are constantly invented (e.g., "iPhone 24"). Some languages, like Chinese, lack spaces between words. These limitations prevent LMs from using all possible words.

Why not just characters?

Using only characters would be too small and complex.

📌 **Example:** "unbelievable" would require 12 separate tokens.

This slows training and makes understanding harder because the same character is used in many words. How can the LM interpret the meaning of "u" in "umbrella" vs. "unwanted"?

Tokens provide a sweet spot between these two approaches.

The next step is to understand how token IDs have embedded meanings that LMs use to understand words.

---

## 9. Understanding Word Embeddings in AI

One of the biggest challenges in artificial intelligence is teaching AI models to understand the meaning of words, not just the letters they are composed of. When an AI system encounters the word "King," it shouldn't just see a combination of the letters k, i, n, and g. It should understand the meaning associated with that word.

As humans, we learn these meanings naturally through interactions. However, AI models can only understand numbers. To bridge this gap, these models use a concept called **embeddings**. By using embeddings, we can teach the meaning of words to AI systems.

### What is an Embedding? 🧐

An embedding is essentially a list of numbers, also known as a **vector**. In mathematics, a vector is a sequence of numbers, similar to an array in Java.

```java
// Example of an array (similar to a vector)
int[] myArray = {1, 2, 3, 4, 5};
```

Each number within the vector represents how strongly a word relates to a specific concept.

### Explaining Embeddings with an Example 📌

Let's imagine someone asks you to explain the meaning of the word "King." You might say that a King is a male person who typically rules a kingdom. They possess characteristics like royalty, powerful leadership, fine clothing, wealth, and command over an army. These characteristics collectively represent the meaning of "King."

Similarly, a Language Model (LM) uses a vector of numbers to remember the meaning of a word like "King."

Let's consider a vector with five dimensions, where each dimension represents a characteristic of the word:

1.  Royalty
2.  Femaleness
3.  Leadership
4.  Strength
5.  Fictional

⚠️ **Warning:** In reality, LM models use thousands of dimensions, not just five. We use a smaller number for simplicity.

For example, the vector for "King" might look like this:

`[9.8, 0.1, 9.2, 9.7, 0.3]`

This indicates that "King" has a high degree of royalty (9.8), a low degree of femaleness (0.1), strong leadership (9.2), high strength (9.7), and a low degree of being fictional (0.3).

If we take a similar vector for "Queen," some dimensions will be similar:

`[9.5, 8.0, 7.0, 6.0, 0.3]`

*   Royalty: 9.5 (similar to King)
*   Femaleness: 8.0 (high, as Queen is female)
*   Leadership: 7.0 (potentially lower than King, depending on the training data)
*   Strength: 6.0 (potentially lower than King)
*   Fictional: 0.3 (low, similar to King)

📝 **Note:** These numbers are determined during the training phase through complex mathematical operations.

### Token IDs and Model Vocabulary 🗂️

The vector calculated for "King" during training might be assigned a token ID, such as 1342. Similarly, "Queen" might be assigned a token ID of 39. These details are stored in the model's vocabulary.

In the future, when a user asks a question involving the word "King," the model can understand its meaning based on the associated vector. It knows that a "King" is a human with characteristics like royalty, leadership, and strength, with minimal femaleness or fictional attributes.

💡 **Tip:** AI engineers do not manually assign these values or dimensions. The LM model dynamically determines and updates them during the training phase. We don't fully understand the internal workings; these are theoretical explanations.

### Embedding Size and High-Dimensional Space 🌌

LM models use thousands of dimensions. For instance:

*   GPT-3: Embedding size of 12,288 (estimated)
*   Llama (Large Model): Large embedding size (estimated)

With such large embedding sizes, each token or word becomes a vast list of numbers capturing subtle meanings and patterns.

Since embedding vectors are very long, they exist in a high-dimensional space where similar tokens tend to cluster together.

Imagine a cluster representing "King." Within this cluster, you'll find related tokens like "Queen" and "Kingdom," all positioned closely based on their semantic similarity.

We'll continue this discussion in the next section.

---

## 10. Understanding Embeddings and Vectors in Language Models

Let's delve deeper into embeddings and vectors in the context of Language Models (LMs).

When a token, like "king," is converted into a vector, it's represented by a series of numbers. This vector might have thousands of dimensions, each number representing a characteristic of the token.

We'll explore how these numbers are calculated by the LM during training.

### Training and Pattern Recognition 🤖

During training, the model processes thousands of sentences, 📌 **Example:**

*   "King and queen rule the land."
*   "The lion is the king of the jungle."
*   "A rich king lived in a big palace."
*   "The Queen wears a crown."

Through this, the model identifies patterns. It notices that "king" appears in contexts of power, royalty, and male gender. "Queen" appears in similar contexts but is associated with female gender. "Lion" appears in powerful or animal contexts.

As the model encounters new patterns, it adjusts the vector numbers. Similar words get similar embeddings, while differences (like gender or species) are captured in different dimensions.

Somewhere within these vector numbers, a dimension will represent whether a word belongs to a human or an animal. For "lion," the animal dimension will have a higher value, while for "king," it will be very low.

### Arithmetic Operations on Vectors ➕➖✖️➗

Since these vector numbers are calculated using mathematical operations, LMs can perform arithmetic operations on them to find other vectors with different meanings.

📌 **Example:** The famous "king - man + woman = queen" example.

If an LM has embedded meanings for "king" and "man," subtracting the "man" vector from the "king" vector yields a resultant vector. Adding the "woman" embedding to this resultant vector produces a vector similar to the "queen" vector.

Subtracting "man" from "king" essentially extracts the meaning of "royalty." Adding this "royalty" vector to "woman" results in "queen."

This is how LMs perform arithmetic and mathematical operations behind the scenes. This requires powerful GPUs capable of running billions of mathematical operations simultaneously using thousands of cores in parallel.

### High-Dimensional Space 🌌

LMs maintain a high-dimensional space, often with more than 10,000 dimensions. Inside this space, individual dimensions represent concepts like gender or royalty.

📝 **Note:** These dimensions aren't manually labeled. LMs automatically build them based on the patterns they observe during learning.

📌 **Example:**

*   **Gender Axis:** Male ↔ Female
*   **Royalty Axis:** Commoner ↔ Royalty
*   **Living Axis:** Object ↔ Animal
*   **Richness Axis:** Poor ↔ Rich
*   **Strength Axis:** Weak ↔ Powerful

These are fictional names we give to these dimensions for clarity. The LM dynamically decides the details during training.

### Semantic Relationships and Geometry 📐

By using these dimensions and arithmetic calculations, the semantic relationships between words are captured geometrically.

Let's revisit the "king - man + woman = queen" example.

Imagine a two-dimensional space:

*   **X-axis:** Gender (Male ↔ Female)
*   **Y-axis:** Royalty (Common ↔ Royal)

In this space:

*   "King" is positioned towards the male and royal end.
*   "Queen" is positioned towards the female and royal end.
*   "Man" and "Woman" are positioned towards the common end, with their respective genders.

As the model moves from "king" to "man," it encounters words like "minister." If "king" moves towards "man," royalty is removed. If "king" moves towards "queen," maleness is removed.

These two-dimensional vector numbers represent the relationships between these words.

Words that are conceptually similar are close in vector space. These relationships are represented by mathematical operations. This allows LMs to understand analogies, comparisons, and complex relationships by performing geometry with meaning.

### Another Example: Country and Authority 🗺️

Consider a scenario with a country dimension on the x-axis and an authority dimension on the y-axis.

During training, the LM reads sentences like:

*   "Paris is the capital of France."
*   "France's capital is Paris."

From this, it learns to create a geography vector that captures the "capital of" relationship.

Countries (France, Germany, India) are positioned towards the country end. Capital cities (Paris, Berlin, Delhi) are positioned towards the authority end.

The LM understands that a capital city is a place where authority resides (rules, laws, constitutions).

The distance from "France" to "Paris" represents the "capital of" relationship.

📌 **Example:**

*   "Capital" might have an embedded meaning of (0.2, 0.9) - low on country, high on authority.
*   "France" might have an embedded meaning of (0.9, 0.2) - high on country, low on authority.
*   "Paris" might have an embedded meaning of (0.9, 0.9) - high on both country and authority.

If asked "What is the capital of France?", the LM recognizes "capital + France" and performs calculations to identify the closest answer, which would be "Paris."

The LM has multiple options to select from, based on probability numbers and temperature configurations. These parameters influence the predicted token or word.

### Initial Vector Numbers 🔢

At the start of LM training, the vector numbers under each dimension are initialized.

The LM identifies uncommon words and special characters to prepare a vocabulary. Each word is assigned a token ID, and each token ID is assigned a random initial value (or all zeros in some models).

These initial vectors have no meaning, as the dimensions are yet to be formed.

As training progresses, the LM reads sentences and updates the numbers under each dimension for each token.

At each point, the LM tries to predict the next word or fill in missing words. If predictions are wrong, the model calculates the loss and updates the vector numbers for words like "king" and surrounding words with similar contexts.

After millions of training examples, the LM builds a proper vector number for "king," with a meaningful definition, settling near words like "queen," "prince," and "royal."

---

## 11. Understanding Token Relationships and Embeddings in Language Models

Large language models (LLMs) don't just perform addition; they also perform multiplication to understand the relationships between tokens. ➕ ✖️

By using multiplication on embedding numbers, LLMs can determine how closely related different tokens are.

### Dot Product: A Simple Math Trick 🧮

LLMs use a simple mathematical technique called the **dot product** to achieve this.

*   It involves multiplying each number in one vector by the corresponding number in another vector.
*   After multiplying all corresponding numbers, the results are added together.
*   If the resulting number is:
    *   Positive: Indicates the words are related. 👍
    *   Zero: Indicates the words are unrelated. 😐
    *   Negative: Indicates the words have opposite meanings (e.g., large vs. small). 👎

📌 **Example:** King and queen have vectors that are close because their meanings are related. Monarchy, also related, is clustered nearby. King and banana, being unrelated, are far apart.

📝 **Note:** A higher positive number indicates a stronger relationship between tokens.

### Vector Dimensions and Projection 📐

Let's look at a simplified example using three dimensions to represent vectors for "king" and "queen":

*   King: \[9, -5, 6]
*   Queen: \[8, -4, 6]

The dot product calculation would be:

`(9 * 8) + (-5 * -4) + (6 * 6) = 72 + 20 + 36 = 128`

The result, 128, is a high positive number, confirming the strong relationship.

LLMs might have thousands of dimensions in their vectors, but sometimes only specific dimensions are relevant (e.g., royalty, gender, human for "king").

To focus on these relevant dimensions, LLMs use a technique called **projection**.

*   Projection squashes a high-dimensional space into a smaller one, focusing on specific topics. 📉

### Dynamic and Contextual Projections ⚙️

When an input query is given to an LLM:

1.  The words are tokenized and converted into high-dimensional embedding vectors. ➡️
2.  These embeddings pass through multiple layers of neural networks (e.g., attention layers, linear projections). 🧠
3.  The model learns which parts of the vector space are important for the task. 📚
4.  The model performs dynamic and contextual projections. 🔄
5.  The model produces an output based on these filtered representations. 🗣️

📌 **Example:** If asked, "What sound does a dog make?", the model focuses on dimensions like animals, sound, and pets, while disregarding unrelated dimensions like currency, architecture, and sports. 🐕 🔊

This smart compression helps LLMs provide quick and relevant outputs.

### Static Embeddings 🧱

The embeddings we've discussed so far are called **static embeddings**.

*   During training, LLMs calculate embeddings for each token with a fixed vector size and mathematical meaning.
*   The vector meaning of "king" is always the same, regardless of surrounding words.
*   These static embeddings are stored in a model's vocabulary lookup table, where each token ID indexes its corresponding vector.

### The Problem with Static Embeddings ⚠️

Blindly following static embeddings can cause issues.

📌 **Example:** Consider these two sentences:

1.  He went to the bank to deposit money. 🏦
2.  She sat by the bank of the river. 🏞️

The word "bank" has different meanings in each sentence. Static embeddings alone cannot distinguish between these meanings.

Unless the LLM understands the context, it cannot perform its job efficiently.

### Overcoming the Drawbacks: Context is Key 🔑

Transformer architectures and attention layers are used to understand the context of a word. More on this in the next lecture! 🚀

---

## 12. Understanding Positional Encoding in Transformer Models

The "Attention is All You Need" paper introduced a new transformer architecture that is foundational for building efficient Language Model (LM) models. This architecture relies heavily on the concept of **positional encoding**.

The process begins with input embeddings, which are static embeddings calculated during training and stored as part of the model's vocabulary. 

Before these embeddings are fed into the transformer model, an additional step called **positional encoding** is applied. Let's delve into why this is crucial.

### The Importance of Positional Encoding 📍

Consider these two sentences:

1.  The cat sat on the mat.
2.  The mat sat on the cat.

Both sentences contain the same words, but their meanings are drastically different due to the order of the words. The position of words significantly impacts the overall meaning of a sentence.

To address this, positional encoding is used. For example, in the first sentence, the word "cat" is at position two. This positional information is conveyed to the LM model by adding positional embedding details to the initial token embedding.

The algorithm retrieves the static embedding value of "cat" from the model's vocabulary and adds the corresponding positional embedding value. The resulting updated token embedding is then passed as input to the LM model.

This final vector captures both:

*   The meaning from the static embedding.
*   The position details from the positional embedding.

### Why Positional Encoding is Necessary ❓

A common question arises: If LM models process sentences in parallel, why is positional information needed?

The answer is that LM models **do not** process tokens sequentially. They process all words or tokens in parallel to achieve efficiency. Therefore, knowing the position of each token is essential.

This final vector, enriched with positional information, is fed into the transformer architecture to resolve potential ambiguities.

📌 **Example:** Consider the sentence, "He went to the bank to deposit the money." and "She sat by the bank of the river." The word "bank" has different meanings based on context.

However, positional embeddings alone cannot fully resolve the ambiguity of the word "bank". They provide positional context, but the **attention layers** within the transformer architecture are crucial for understanding the actual meaning by examining the surrounding words.

We will explore these attention layers in the next section.

---

## 13. Understanding the Attention Layer in Language Models

The attention layer is a crucial component within the transformer architecture of Language Models (LMs). It plays a significant role, acting as a "smart highlighter" 💡, enabling the model to focus on the most relevant words in a sentence when understanding or predicting a specific word.

### The Role of Attention

The attention layer helps LMs determine which words matter most when processing a sentence. It's like the model asking: "To understand this word, which other words are most important?" This is particularly useful when dealing with words that have multiple meanings.

📌 **Example:** The word "bank" can refer to a financial institution or the edge of a river.

### Resolving Ambiguity with Attention

Let's explore how the attention layer helps resolve ambiguity:

1.  **Analyzing Context:** The model examines the surrounding words to understand the intended meaning.
2.  **Calculating Attention Scores:** Based on the context, the attention layer assigns scores to each word, indicating its relevance.
3.  **Interpreting Meaning:** The LM uses these scores to determine the correct meaning of the ambiguous word.

#### 📌 **Example 1: Financial Bank**

Sentence: "I went to the bank to deposit a check."

*   The model analyzes the context and identifies words like "deposit" and "check" as strong indicators of a financial transaction.
*   The attention layer assigns higher scores to these words.
*   The LM concludes that "bank" refers to a financial institution.

#### 📌 **Example 2: River Bank**

Sentence: "She sat by the bank of the river and watched the sunset."

*   The model identifies words like "river," "sat," and "sunset" as clues related to nature and location.
*   The attention layer assigns higher scores to these words.
*   The LM concludes that "bank" refers to the river's edge.

### LM Workflow: From Input to Output

Let's review how LMs process input and generate output, highlighting the role of the attention layer:

1.  **Training Phase:**
    *   The LM is trained on vast amounts of text data (billions of words and trillions of tokens).
    *   It builds a model vocabulary containing tokens, token IDs, and static embedding values.
    *   Each word is assigned a token ID and a corresponding static embedding vector.

    📌 **Example:**

    ```
    Word: "king"
    Token ID: 361
    Static Embedding: [vector representation]
    ```

2.  **Production Phase:**
    1.  **Input Tokenization:** The user's input is converted into tokens using the model's vocabulary.
    2.  **Embedding Update:** Static embeddings are combined with positional embeddings to capture word order.
    3.  **Transformer Architecture:** The combined embeddings are fed into the transformer architecture, which includes multiple attention layers.
    4.  **Attention Score Calculation:** The attention layers calculate attention scores to understand the context and meaning of each word.
    5.  **Output Generation:** After further processing, the model generates a response in token format.
    6.  **Text Conversion:** The tokens are converted back into human-readable text using the model vocabulary.
    7.  **Display Output:** The final text is displayed to the user.

### Key Takeaways

*   Static embeddings alone are insufficient for LMs to predict accurate outputs.
*   The attention layer is crucial for understanding context and resolving ambiguity.
*   Understanding the jargon (embeddings, tokens, token IDs, vectors, attention layers) is essential for working with LMs.

### Further Exploration

For a deeper dive into LM models and AI in general, consider exploring the "3Blue1Brown" YouTube channel. They offer excellent visual explanations of complex topics.

Suggested videos:

*   Large Language Models explained briefly
*   How might LMs Store facts? Deep learning chapter seven
*   Transformers, the tech behind the LLMs Deep Learning chapter five
*   Attention layer

⚠️ **Warning:** The explanations on "3Blue1Brown" may be fast-paced and use mathematical jargon, assuming some prior knowledge of AI concepts.

From the next lecture, we will shift our focus onto the Spring AI framework.

---