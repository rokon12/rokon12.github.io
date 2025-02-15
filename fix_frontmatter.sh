#!/bin/bash

# Function to clean and quote a value
clean_and_quote() {
    local value="$1"
    # Remove all existing quotes
    value=$(echo "$value" | sed 's/^["'\'']*//;s/["'\'']*$//')
    # If value contains double quotes, escape them and wrap in double quotes
    if [[ $value == *'"'* ]]; then
        value=$(echo "$value" | sed 's/"/\\"/g')
        echo "\"$value\""
    else
        echo "'$value'"
    fi
}

# Loop through all markdown files in backup directory
for file in backup/*.md; do
    # Skip if file doesn't exist
    [ -f "$file" ] || continue

    # Create a temporary file
    temp_file=$(mktemp)

    # Read the file content after front matter
    tail -n +6 "$file" > "$temp_file"

    # Extract front matter values
    title=$(head -n 5 "$file" | grep "^title:" | sed 's/^title: //')
    url=$(head -n 5 "$file" | grep "^original_url:" | sed 's/^original_url: //')
    date=$(head -n 5 "$file" | grep "^date_scraped:" | sed 's/^date_scraped: //')

    # Clean and quote values
    title_quoted=$(clean_and_quote "$title")
    url_quoted=$(clean_and_quote "$url")
    date_quoted=$(clean_and_quote "$date")

    # Create new front matter
    {
        echo "---"
        echo "title: $title_quoted"
        echo "original_url: $url_quoted"
        echo "date_scraped: $date_quoted"
        echo "---"
        cat "$temp_file"
    } > "$file"

    # Clean up
    rm "$temp_file"
done

echo "Front matter has been fixed in all markdown files"
