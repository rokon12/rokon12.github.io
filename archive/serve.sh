#!/bin/bash

# Simple script to run Jekyll locally
echo "🚀 Starting Jekyll server..."
echo "📦 Installing dependencies if needed..."

# Check if Jekyll is installed
if ! command -v jekyll &> /dev/null; then
    echo "Jekyll is not installed. Please install it with:"
    echo "  gem install jekyll bundler"
    exit 1
fi

# Install dependencies
bundle install

# Run Jekyll with correct baseurl for local development
echo ""
echo "🌐 Starting server at http://localhost:4000"
echo "   (Note: Use http://localhost:4000 instead of http://localhost:4000/rokon12)"
echo ""
echo "Press Ctrl+C to stop the server"
echo ""

# Run Jekyll without the baseurl for local development
bundle exec jekyll serve --baseurl "" --watch