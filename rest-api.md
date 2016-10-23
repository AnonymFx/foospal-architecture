# REST API

## Web Frontend
### GET /  

[//]: # (---------------------------------------------------------------------------)

## Game

### PUT /api/game/{id}
May not change already existing game score
Body:
1. Score of team 1
2. Score of team 2

### POST /api/game/{id}
May change already existing game score
Body:
1. Score of team 1
2. Score of team 2

[//]: # (---------------------------------------------------------------------------)

## Tournament

### PUT /api/tournament/{id}/nextPhase

### PUT /api/tournament/{id}/match/new -> matchid

### PUT /api/tournament/new -> tournamentid
Body: 
1. Settings for the tournament like tournament type, etc. (c.f. TournamentSettings in architecture diagram)
2. Initial player list

### PUT /api/tournament/{id}/player/add 
Body: PlayerId

### GET /api/tournaments/{index}  
Get index*20 til (index+1)*20-1

### GET /api/tournament/{id}  
Get tournament with {id} {id}

### GET /api/tournament/latest
Get latest tournament

[//]: # (---------------------------------------------------------------------------)

## Match

### DELETE /api/match/{id}

### GET /api/match/{id}

[//]: # (---------------------------------------------------------------------------)

## Payer

### GET /api/players/{index}
Get index*20 til (index+1)*20-1

### PUT /api/player/add -> {id}

### DELETE /api/player/{id}

### GET /api/player/{id}
