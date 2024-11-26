main:
	@#

.PHONY : build run sb repo-push repo-login

build:
	@docker build . -t dedacosta/giftmas

run:
	@docker run --env-file ./.env -dit --name giftmas -p 1000:1000 dedacosta/giftmas /bin/sh

sb:
	@set -a; source ./.env; java -jar target/giftmas.jar

repo-push:
	@docker push dedacosta/giftmas:latest

repo-login:
	@docker push dedacosta/giftmas:latest