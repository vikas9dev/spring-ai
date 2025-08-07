## Model Vocabulary in Language Models

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
