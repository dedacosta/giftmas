
PROXY := http://localhost:8080

BACKUP := mkdir -p backups; cp import.json import.json.bk 2>/dev/null || :; cp import.json backups/import-$$(date +%Y%m%d%H%M%S).json 2>/dev/null || :

main:
	@#

.PHONY : persons person import export show send clear delete

persons:
	@curl -s -X GET $(PROXY)/control/persons | python -m json.tool

person :
	@curl -s -X GET $(PROXY)/control/person/$(id)
	@echo -e ""

send:
	@curl -s -X POST  $(PROXY)/control/send
	@echo -e ""

show:
	@curl -s -X GET $(PROXY)/control/show | python -m json.tool

import:
	@curl -s -X DELETE $(PROXY)/control/persons
	@curl -s -X POST -H 'Content-Type: application/json' -d @import.json $(PROXY)/control/persons
	@echo -e ""

export:
	@$(BACKUP)
	@curl -s -X GET  $(PROXY)/control/persons | python -m json.tool > import.json

clear:
	@$(BACKUP)
	@curl -s -X DELETE $(PROXY)/control/persons
	@curl -s -X GET  $(PROXY)/control/persons | python -m json.tool > import.json

delete:
	@$(BACKUP)
	@curl -s -X DELETE $(PROXY)/control/person/$(id)
	@curl -s -X GET  $(PROXY)/control/persons | python -m json.tool > import.json
