msg=${1:?commit}
sh gitsub.sh commit -m "$msg" -a
sh gitsub.sh push origin 2.0
git add lib plugin setup demo 
git commit -m "$msg"
git push origin 2.0
