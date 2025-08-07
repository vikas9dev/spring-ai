## Understanding Word Embeddings in AI

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
