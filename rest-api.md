# REST API

## Web Frontend
### GET /  

[//]: # (---------------------------------------------------------------------------)

## Game

### PUT /api/games/{id}
May not change already existing game score
Body:
1. Score of team 1
2. Score of team 2

### POST /api/games/{id}
May change already existing game score
Body:
1. Score of team 1
2. Score of team 2

[//]: # (---------------------------------------------------------------------------)

## Tournament

### PUT /api/tournaments/{id}/nextPhase

### PUT /api/tournaments/{id}/match/new -> matchid

### PUT /api/tournaments/new -> tournamentid
Body:
1. Settings for the tournament like tournament type, etc. (c.f. TournamentSettings in architecture diagram)
2. Initial player list

### PUT /api/tournaments/{id}/player/add
Body: PlayerId

### GET /api/tournaments/page{index}  
Get index\*20 til (index+1)\*20-1

### GET /api/tournaments/{id}  
Get tournament with {id} {id}

### GET /api/tournaments/latest
Get latest tournament

[//]: # (---------------------------------------------------------------------------)

## Match

### DELETE /api/matches/{id}

### GET /api/matches/{id}

[//]: # (---------------------------------------------------------------------------)

## Payer

### GET /api/players/page{index}
Get index\*20 til (index+1)\*20-1

### PUT /api/players/add -> {id}

### DELETE /api/players/{id}

### GET /api/players/{id}
