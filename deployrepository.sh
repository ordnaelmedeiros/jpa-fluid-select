echo "Start deploy maven repository"

echo "Commit Message:"
read commitMessage

echo "Message: $commitMessage"

git add .
git commit -m "$commitMessage"

mvn release:clean -f "lib"
mvn release:prepare -f "lib"

git push --tags
git push

mvn release:perform -f "lib"

git push
