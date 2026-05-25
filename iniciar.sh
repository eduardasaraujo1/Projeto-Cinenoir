#!/bin/bash
echo "Subindo backend..."
cd ~/Projeto-Cinenoir/cinema-backend
mvn spring-boot:run > /tmp/backend.log 2>&1 &
echo "Aguardando backend..."
sleep 15
echo "Subindo frontend..."
cd ~/Projeto-Cinenoir/cinema-frontend
python3 -m http.server 3000 > /tmp/frontend.log 2>&1 &
echo "✅ CineNoir rodando!"
echo "   Frontend: http://localhost:3000"
echo "   Backend:  http://localhost:8080"
