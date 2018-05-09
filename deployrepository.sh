echo "Start deploy maven repository"

echo "Commit Message:"
read commitMessage

echo "Message: $commitMessage"

git add .
git commit -m "$commitMessage"
git push

cd lib

mvn release:clean
mvn release:prepare

git push --tags
git push

mvn release:perform

git push
