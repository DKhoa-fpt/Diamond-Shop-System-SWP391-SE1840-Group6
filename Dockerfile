# stage 1
FROM node:latest as node
WORKDIR /app
COPY . .
RUN npm install --force
RUN npm run build 

# stage 2
FROM nginx:alpine
COPY --from=node /app/dist/lte-angular /usr/share/nginx/html
