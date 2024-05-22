# Use the official Node.js 18 image as a base
FROM node:18

# Set the working directory in the container
WORKDIR /app

# Copy package.json and yarn.lock files
COPY package.json yarn.lock ./

# Install dependencies
RUN yarn install

# Copy the rest of the application code
COPY . .

#  Build the application
# RUN yarn build

# Expose port 5731
EXPOSE 5731


CMD ["yarn", "dev"]
