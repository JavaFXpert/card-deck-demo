let playingCards = [];
let cardStr = ""; // Comma-delimited string of card codes held in the client

// score nodes
let messageDisplayNode = document.getElementById("message-display");
// let playerScoreNode = document.getElementById("player-number");

// card area nodes
let playingCardsNode = document.getElementById("playing-cards");

// other nodes
let announcementNode = document.getElementById("announcement");
let newDeckNode = document.getElementById("new-game")
let nextHandNode = document.getElementById("next-hand");
let hitMeNode = document.getElementById("hit-me");
let stayNode = document.getElementById("stay");
let randomShuffleNode = document.getElementById("random-shuffle");
let dealPokerHandNode = document.getElementById("deal-poker-hand");
let shuffleDealNode = document.getElementById("shuffle-deal");
let onlyHeartsNode = document.getElementById("only-hearts");


// On click events
newDeckNode.onclick = () => dealCards('newDeck');
nextHandNode.onclick = () => dealCards('cutCards');
hitMeNode.onclick = () => dealCards('overhandShuffle');
stayNode.onclick = () => dealCards('riffleShuffle');
randomShuffleNode.onclick = () => dealCards('randomShuffle');
dealPokerHandNode.onclick = () => dealCards('dealPokerHand');
shuffleDealNode.onclick = () => dealCards('shuffleDeal');
onlyHeartsNode.onclick = () => dealCards('onlyHearts');

function dealCards(modeArg) {
    var fetchStr = "http://localhost:8080/cards/deck/new?numcards=52";
    if (modeArg === "newDeck") {
        fetchStr = "http://localhost:8080/cards/deck/new?numcards=52";
    }
    else if (modeArg === "cutCards") {
        fetchStr = "http://localhost:8080/cards/deck/cut?cards=" + cardStr;
    }
    else if (modeArg === "overhandShuffle") {
        fetchStr = "http://localhost:8080/cards/deck/overhandshuffle?cards=" + cardStr;
    }
    else if (modeArg === "riffleShuffle") {
        fetchStr = "http://localhost:8080/cards/deck/riffleshuffle?cards=" + cardStr;
    }
    else if (modeArg === "randomShuffle") {
        fetchStr = "http://localhost:8080/cards/deck/randomshuffle?cards=" + cardStr;
    }
    else if (modeArg === "dealPokerHand") {
        fetchStr = "http://localhost:8080/cards/deck/dealpokerhand?cards=" + cardStr;
    }
    else if (modeArg === "shuffleDeal") {
        fetchStr = "http://localhost:8080/cards/deck/shuffledeal?cards=" + cardStr;
    }
    else if (modeArg === "onlyHearts") {
        fetchStr = "http://localhost:8080/cards/deck/hearts";
    }
    resetPlayingArea();
    nextHandNode.style.display = "block";
    hitMeNode.style.display = "block";
    stayNode.style.display = "block";
    randomShuffleNode.style.display = "block";
    dealPokerHandNode.style.display = "block";
    shuffleDealNode.style.display = "block";

    fetch(fetchStr)
        .then(res => res.json())
        .then(res => {
          cardStr = "";
          res.cards.forEach((c, i) => {
              playingCards.push(c);
              let cardDomElement = document.createElement("img");
              if(i===-1) {
                  cardDomElement.src = 'http://127.0.0.1:8080/images/gray_back_reactor.png';
              }
              else {
                  cardDomElement.src = c.image;
              }
              cardStr += c.code;
              if (i < res.cards.length - 1) {
                  cardStr += ",";
              }
              playingCardsNode.appendChild(cardDomElement)
              messageDisplayNode.textContent = res.name;
          })

        })
        .catch(console.error)
}


function newHand() {
  resetPlayingArea();

  //fetch(`https://deckofcardsapi.com/api/deck/${deckID}/draw/?count=4`)
  fetch(`http://localhost:8080/carddeck?shuffled=true&request=4`)
  .then(res => res.json())
  .then(res => {
    // hitMeNode.style.display = "block";
    // stayNode.style.display = "block";

    messageDisplayNode.textContent = dealerScore;

    playingCards.forEach((card, i) => {
      let cardDomElement = document.createElement("img");
      if(i===0) {
        cardDomElement.src = 'http://127.0.0.1:8080/images/gray_back_reactor.png';
      } else {
        cardDomElement.src = card.image;
      }
      playingCardsNode.appendChild(cardDomElement)
    })

  })
  .catch(console.error)
}


function resetPlayingArea() {
  playingCards = [];
  roundLost = false;
  roundWon = false;
  roundTied = false;
  messageDisplayNode.textContent = "";
  while (playingCardsNode.firstChild) {
    playingCardsNode.removeChild(playingCardsNode.firstChild);
  }
}


