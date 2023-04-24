# Use a base image
FROM ubuntu:18.04

# Update package lists
RUN apt-get update

# Install nginx
RUN apt-get install -y nginx

# Start nginx
CMD ["nginx", "-g", "daemon off;"]

