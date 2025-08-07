## Understanding Embeddings and Vectors in Language Models

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
