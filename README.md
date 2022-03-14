# URL shortening Service

###A simple URL shortening Service (like https://bitly.com/). 

The Service provides the following features:

**Shortening**: Take a URL and return a much shorter URL.
- The input URL format must be valid
- Maximum character length for the hash portion of the URL is 6
- The Service should return a meaningful message with a suitable status cod

_Eg: https://www.daimler.com/karriere/berufserfahrene/direkteinstieg/ => http://daiml.er/GUKA8w/_

**Redirection**: Take a short URL and redirect to the original URL.

_Eg: http://daiml.er/GUKA8w/ => https://www.daimler.com/karriere/berufserfahrene/direkteinstieg/_

**Custom URL**: Allow the users to pick custom shortened URL.

_Eg: http://www.daimler.com/karriere/jobsuche/ => http:// daiml.er/jobs_


### Extras
- Swagger available on the following URL:[Swagger UI](http://localhost:8086/swagger-ui/)

- Actuator available on the following URL:[actuator](http://localhost:8086/actuator)
