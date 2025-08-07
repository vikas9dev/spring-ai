## Understanding Token Relationships and Embeddings in Language Models

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
