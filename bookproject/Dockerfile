# 1️⃣ build stage
FROM node:20 AS build
WORKDIR /app

COPY package*.json ./
RUN npm install

# 🔥 env 먼저
COPY .env.production ./

# 소스 복사
COPY . .

# 🔥 여기서 build
RUN npm run build


# 2️⃣ nginx stage
FROM nginx:alpine
COPY --from=build /app/dist /usr/share/nginx/html

EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
