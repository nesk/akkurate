# Ktor Client

Ktor Client is a framework for building asynchronous client-side applications. %product% provides an integration to
automatically validate downloaded data.

![A code example of the Ktor Client integration, used to showcase %product% on social networks.](social-ktor-client.png)
{width="700" border-effect="rounded"}

## Installation

Before using %product% with Ktor, you need to add the following dependency to your Gradle script:

<procedure title="Add the %product% plugin for Ktor" id="install-akkurate">

<code-block lang="kotlin">
implementation("dev.nesk.akkurate:akkurate-ktor-client:%version%")
</code-block>

</procedure>

> The [Content Negotiation](https://ktor.io/docs/client-serialization.html) plugin is also required.

{style="note"}

## Validate on deserialization

We want to retrieve [the latest releases of Akkurate](https://github.com/nesk/akkurate/releases),
using [GitHub's API](https://api.github.com/repos/nesk/akkurate/releases):

<compare type="top-bottom" first-title="Code" second-title="Output">

```kotlin
@Validate
@Serializable
data class Release(val name: String)

suspend fun main() {
    val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
    }

    val apiUrl = "https://api.github.com/repos/nesk/akkurate/releases"
    val response: HttpResponse = client.get(apiUrl)
    val data: List<Release> = response.body()
    println(data)
}
```

```text
[
    Release(name=Akkurate 0.10.0),
    Release(name=Akkurate 0.9.1),
    Release(name=Akkurate 0.9.0), 
    ...
]
```

</compare>


Although this API is probably well maintained, we fear potential issues on the API side, like bugs or breaking changes.
To protect ourselves, we want to validate the response body:

```kotlin
val validateReleaseList = Validator<List<Release>> {
    each {
        name.isNotEmpty()
    }
}
```

And we register this validator in our client:

```kotlin
install(Akkurate) {
    registerValidator(validateReleaseList)
}
```

Now, when deserializing the response body, the `Release` instances are automatically validated, and an exception is
thrown when invalid:

```kotlin
try {
    val data: List<Release> = response.body()
    println(data)
} catch (exception: ValidationResult.Exception) {
    System.err.println("Invalid response: ${exception.violations}")
}
```

> Try adding a failing constraint to the validator, like `name.isEmpty()`, to see the exception thrown by %product%.

<seealso style="cards">
  <category ref="related">
    <a href="ktor-server-integration.md" />
    <a href="ktor-validation-tutorial.md" />
  </category>
</seealso>
