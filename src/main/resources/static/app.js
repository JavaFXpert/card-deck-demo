let playingCards = [];
let cardStr = ""; // Comma-delimited string of card codes held in the client
let cardBackImg = 'http://127.0.0.1:8080/images/gray_back_reactor.png';

// mmessage display node
let messageDisplayNode = document.getElementById("message-display");

// card area nodes
let playingCardsNode = document.getElementById("playing-cards");

// other nodes
let newDeckNode = document.getElementById("new-deck")
let onlyHeartsNode = document.getElementById("only-hearts");
let cutDeckNode = document.getElementById("cut-deck");
let overhandShuffleNode = document.getElementById("overhand-shuffle");
let riffleShuffleNode = document.getElementById("riffle-shuffle");
let randomShuffleNode = document.getElementById("random-shuffle");
let dealPokerHandNode = document.getElementById("deal-poker-hand");
let shuffleDealNode = document.getElementById("shuffle-deal");


// On click events
newDeckNode.onclick = () => dealCards('newDeck');
onlyHeartsNode.onclick = () => dealCards('onlyHearts');
cutDeckNode.onclick = () => dealCards('cutCards');
overhandShuffleNode.onclick = () => dealCards('overhandShuffle');
riffleShuffleNode.onclick = () => dealCards('riffleShuffle');
randomShuffleNode.onclick = () => dealCards('randomShuffle');
dealPokerHandNode.onclick = () => dealCards('dealPokerHand');
shuffleDealNode.onclick = () => dealCards('shuffleDeal');

function dealCards(modeArg) {
    var fetchStr = "http://localhost:8080/cards/deck/new?numcards=52";
    if (modeArg === "newDeck") {
        //fetchStr = "http://localhost:8080/cards/deck/new?numcards=52";
        fetchStr = "http://localhost:8080/newdeck?numcards=52";
    }
    else if (modeArg === "onlyHearts") {
        fetchStr = "http://localhost:8080/cards/deck/Hearts";
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
    resetPlayingArea();
    newDeckNode.style.display = "block";
    onlyHeartsNode.style.display = "block";
    cutDeckNode.style.display = "block";
    overhandShuffleNode.style.display = "block";
    riffleShuffleNode.style.display = "block";
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
              cardDomElement.src = c.image;
              cardDomElement.alt = c.image;
              cardDomElement.addEventListener("click",
                  evt => cardDomElement.src = (cardDomElement.src == cardDomElement.alt ? cardBackImg : cardDomElement.alt));


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


