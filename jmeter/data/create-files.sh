#!/bin/zsh

echo "value,accountId;" > post-create-$1.csv;
chmod +x post-create-$1.csv;

for i in {1..$2};
do
	echo "$i,$(($i * 2))" >> post-create-$1.csv;
done;

