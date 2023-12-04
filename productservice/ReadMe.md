# Build and deploy
`mvn clean install`

`docker build -t product-service-image:v1 .`

`kubectl apply -f ./build/kube/secrets.yaml`

`kubectl apply -f ./build/kube/service.yaml`

`kubectl apply -f ./build/kube/deployment.yaml`

# Create a category
`curl --location 'product-service:<node_port>/api/v1/categories' \
--header 'Content-Type: application/json' \
--header 'Cookie: JSESSIONID=FBA2401B41EC131FBC6A1FE5DEA3B8D6' \
--data '{
"name": "Men'\''s T-shirts"
}'`

# Create a product
`curl --location 'product-service:<node_port>/api/v1/products' \
--header 'Content-Type: application/json' \
--header 'Cookie: JSESSIONID=FBA2401B41EC131FBC6A1FE5DEA3B8D6' \
--data '{
"name": "Lee Jeans",
"price": 1500,
"category": {
"id": 1
},
"quantityAvailable": 100
}'`

# Get products
`curl --location 'http://localhost:<node_port>/api/v1/products' \
--header 'Cookie: JSESSIONID=FBA2401B41EC131FBC6A1FE5DEA3B8D6'`