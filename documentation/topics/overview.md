# Overview

%product% is a validation library taking advantage of the expressive power of Kotlin. No need \
for 30+ annotations or complicated custom constraints; write your validation code in Kotlin with a beautiful declarative
API.

Designed from scratch to handle complex business logic, its role is to help you write qualitative and maintainable
validation code.

> %product% is under development and, despite being heavily tested, its API isn't yet stabilized; _breaking changes
> might happen on minor releases._ However, we will always provide migration guides.
>
> Report any issue or bug <a href="%github_product_url%">in the GitHub repository.</a>

{style="warning"}

## Features

- **Beautiful DSL and API.** Write crystal clear validation code and keep it <tooltip term="DRY">DRY</tooltip>. Use
  loops and conditions when needed; forget about annotation hell.
- **Bundled with all the essential constraints.** Write custom constraints for your business logic and nothing more. The
  rest? It's on us!
- **Easily and highly extendable.** No need to write verbose code to create custom constraints, within %product% they're
  as simple as a lambda with parameters.
- **Contextual and asynchronous.** Query sync/async data sources whenever you need to, like a database, or a REST API.
  Everything can happen inside validation.
- **Integrated with your favorite tools. _(SOON)_** Validate your data within any popular framework, we take care of the
  integrations for you.
- **Code once, deploy everywhere. _(SOON)_** Take advantage of Kotlin Multiplatform; write your validation code once
  then use it in your front-end and back-end code.
