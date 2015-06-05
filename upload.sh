if [ -z "$1" ] ; then
  echo Usage: $0 <talk directory>
  exit
fi

if [ ! -d "$1" ] ; then
  echo $1 is not a directory. Are you in the right spot?
  exit
fi

rsync -rv $1 arnebrasseur@arnebrasseur.net:subdomains/www/talks/
