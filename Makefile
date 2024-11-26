main:
	@#

.PHONY : build run sb

build:
	@docker build . -t dedacosta/giftmas

run:
	@docker run --env-file ./.env -dit --name giftmas -p 5000:8080 dedacosta/giftmas /bin/sh

sb:
	@set -a; source ./.env; java -jar target/giftmas.jar