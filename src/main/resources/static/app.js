// app state
let dealerCards = [];
let roundLost = false;
let roundWon = false;
let roundTied = false;
let playerScore = 0;
let dealerScore = 0;
let cardStr = ""; // Comma-delimited string of card codes held in the client

// score nodes
let dealerScoreNode = document.getElementById("dealer-number");
// let playerScoreNode = document.getElementById("player-number");

// card area nodes
let dealerCardsNode = document.getElementById("dealer-cards");

// other nodes
let announcementNode = document.getElementById("announcement");
let newDeckNode = document.getElementById("new-game")
let nextHandNode = document.getElementById("next-hand");
let hitMeNode = document.getElementById("hit-me");
let stayNode = document.getElementById("stay");
let dealPokerHandNode = document.getElementById("deal-poker-hand");
let shuffleDealNode = document.getElementById("shuffle-deal");


// On click events
newDeckNode.onclick = () => dealCards('newDeck');
nextHandNode.onclick = () => dealCards('cutCards');
hitMeNode.onclick = () => dealCards('overhandShuffle');
stayNode.onclick = () => dealCards('riffleShuffle');
dealPokerHandNode.onclick = () => dealCards('dealPokerHand');
shuffleDealNode.onclick = () => dealCards('shuffleDeal');

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
    else if (modeArg === "dealPokerHand") {
        fetchStr = "http://localhost:8080/cards/deck/dealpokerhand?cards=" + cardStr;
    }
    else if (modeArg === "shuffleDeal") {
        fetchStr = "http://localhost:8080/cards/deck/shuffledeal?cards=" + cardStr;
    }
    resetPlayingArea();
    nextHandNode.style.display = "block";
    hitMeNode.style.display = "block";
    stayNode.style.display = "block";
    dealPokerHandNode.style.display = "block";
    shuffleDealNode.style.display = "block";

    fetch(fetchStr)
        .then(cards => cards.json())
        .then(cards => {
          cardStr = "";
          cards.forEach((c, i) => {
              dealerCards.push(c);
              let cardDomElement = document.createElement("img");
              if(i===-1) {
                  cardDomElement.src = 'http://127.0.0.1:8080/images/gray_back_reactor.png';
              }
              else {
                  cardDomElement.src = c.image;
              }
              cardStr += c.code;
              if (i < cards.length - 1) {
                  cardStr += ",";
              }
              dealerCardsNode.appendChild(cardDomElement)
          })

          dealerScore = "?";
          dealerScoreNode.textContent = "Full House";
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

    dealerScore = "?";
    dealerScoreNode.textContent = dealerScore;

    dealerCards.forEach((card, i) => {
      let cardDomElement = document.createElement("img");
      if(i===0) {
        cardDomElement.src = 'http://127.0.0.1:8080/images/gray_back_reactor.png';
      } else {
        cardDomElement.src = card.image;
      }
      dealerCardsNode.appendChild(cardDomElement)
    })

  })
  .catch(console.error)
}


function resetPlayingArea() {
  dealerCards = [];
  roundLost = false;
  roundWon = false;
  roundTied = false;
  dealerScore = "";
  dealerScoreNode.textContent = dealerScore;
  while (dealerCardsNode.firstChild) {
    dealerCardsNode.removeChild(dealerCardsNode.firstChild);
  }
}


