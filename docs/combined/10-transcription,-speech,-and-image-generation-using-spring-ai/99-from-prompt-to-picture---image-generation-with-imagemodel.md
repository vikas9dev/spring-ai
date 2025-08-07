## Generating Images with Spring AI and OpenAI

This section explores how to generate images using Spring AI and OpenAI models. Similar to speech models, image models allow you to generate images based on a given prompt. Let's dive into some demos.

### Creating an Image Controller

First, we'll create a new controller named `ImageController`.

1.  Annotate the class for REST API support.
2.  Inject an `ImageModel` bean into the class.

```java
@RestController
public class ImageController {

    private final ImageModel imageModel;

    public ImageController(ImageModel imageModel) {
        this.imageModel = imageModel;
    }

    // ... more code follows
}
```

The `ImageModel` interface has a single abstract method that accepts an `ImagePrompt` as a request.

### Understanding ImagePrompt

The `ImagePrompt` class offers several constructors. For simple scenarios, you can use the constructor that accepts a prompt message. For more control, you can use constructors that accept an `ImageMessage`.

The `ImageMessage` class includes fields like `text` and `weight`. You can use these to provide detailed instructions. 📌 **Example:** If you want to emphasize a "red car" in front of a building, assigning a higher weight to "red car" will guide the model to prioritize it.

### Building a Simple REST API

Let's create a REST API endpoint to generate images.

1.  Use the `@GetMapping` annotation to map the `/image` path.
2.  Create a method that accepts a `message` request parameter.
3.  Invoke the `call` method of the `ImageModel` with an `ImagePrompt` object.
4.  Extract the image URL from the `ImageResponse`.

```java
@GetMapping("/image")
public String generateImage(@RequestParam("message") String message) {
    ImageResponse imageResponse = imageModel.call(new ImagePrompt(message));
    return imageResponse.getResult().getOutput().getUrl();
}
```

The OpenAI model generates an image, uploads it to a location, and provides a URL. ⚠️ **Warning:** This URL is temporary and expires after a short period.

📝 **Note:** If you need to store the image, use the `getBase64Json` method to get the image data as a byte array in JSON format. You can then create a file and store it in a storage system or database.

### Testing the API

After building the application, invoke the `/image` REST API with a detailed prompt. 📌 **Example:**

```
/image?message=black and white, close up of a smiling woman with closed eyes, her hair framing her face naturally, excluding warmth and joy
```

The more specific you are, the better the generated image will be.

### Configuring Image Options

To customize image generation, use the `OpenAIImageOptions` class.

1.  Create a new REST API endpoint, for example, `/image-options`.
2.  Use `OpenAIImageOptions.builder()` to configure various options.

Available options include:

*   `n`: Number of images to generate (1-10, but only 1 is supported for Dall-E 3).
*   `model`: Image generation model (default is Dall-E 3).
*   `width`: Image width (valid values: 256, 512, 1024).
*   `height`: Image height (valid values: 256, 512, 1024).
*   `quality`: Image quality (`normal` or `hd`).
*   `responseFormat`: Response format (`url` or `b64_json`).
*   `size`: Image size (e.g., "256x256").
*   `style`: Image style (`vivid` or `natural`). `vivid` produces hyper-real images, while `natural` produces more natural-looking images. This is only supported for Dall-E 3.
*   `user`: User identifier (username or email) for monitoring and abuse detection.

```java
@GetMapping("/image-options")
public String generateImageWithOptions(@RequestParam("message") String message) {
    OpenAIImageOptions options = OpenAIImageOptions.builder()
        .n(1)
        .quality("hd")
        .style("natural")
        .height(1024)
        .width(1024)
        .responseFormat("url")
        .build();

    ImageResponse imageResponse = imageModel.call(new ImagePrompt(message, options));
    return imageResponse.getResult().getOutput().getUrl();
}
```

The `user` field is used by OpenAI to provide attribute information under the metadata of the image and to detect abuse. 💡 **Tip:** Providing a user identifier helps OpenAI monitor and prevent abuse, such as generating excessive images.

### Testing with Options

Invoke the `/image-options` endpoint with a message and the configured options. 📌 **Example:**

```
/image-options?message=three people collaborate in a modern office discussing a presentation projected on the wall. Minimalistic design with warm lighting.
```

### Exploring Other Image Models

Spring AI supports various image models. While this example uses OpenAI, you can explore other models like Stability AI. ⚠️ **Warning:** Each model may require a separate billing account. Always refer to the Spring AI documentation for more details.
