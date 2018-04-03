// app state
let deckID = "";
let dealerCards = [];
let playerCards = [];
let roundLost = false;
let roundWon = false;
let roundTied = false;
let playerScore = 0;
let dealerScore = 0;

// score nodes
let dealerScoreNode = document.getElementById("dealer-number");
let playerScoreNode = document.getElementById("player-number");

// card area nodes
let dealerCardsNode = document.getElementById("dealer-cards");
let playerCardsNode = document.getElementById("player-cards");

// other nodes
let announcementNode = document.getElementById("announcement");
let newDeckNode = document.getElementById("new-game")
let nextHandNode = document.getElementById("next-hand");
let hitMeNode = document.getElementById("hit-me");
let stayNode = document.getElementById("stay");


// On click events
newDeckNode.onclick = getNewDeck;
//nextHandNode.onclick = newHand;
nextHandNode.onclick = dealCards;
hitMeNode.onclick = ()=>hitMe('player');
stayNode.onclick = ()=>setTimeout(()=>dealerPlays(), 600);

function dealCards() {
    resetPlayingArea();

    //fetch(`https://deckofcardsapi.com/api/deck/${deckID}/draw/?count=4`)
    //fetch(`http://localhost:8080/carddeck?numcards=5`)
    //fetch(`http://localhost:8080/carddeck?shuffled=true&numcards=6`)
    //fetch(`http://localhost:8080/carddeckbysuit?suit=DIAMONDS&shuffled=true&numcards=7`)
    //fetch(`http://localhost:8080/carddeckmerge`)
    //fetch(`http://localhost:8080/carddeckmergeordered`)
    //fetch(`http://localhost:8080/carddeckmergesort`)
    //fetch(`http://localhost:8080/carddecktakelast`)
    fetch(`http://localhost:8080/carddeckmergewith`)
        .then(cards => cards.json())
        .then(cards => {
          hitMeNode.style.display = "block";
    stayNode.style.display = "block";

    //dealerCards.push(cards[0], cards[1], cards[2], cards[3]);
    cards.forEach((c, i) => {
        dealerCards.push(c);
    })

    dealerScore = "?";
    dealerScoreNode.textContent = dealerScore;

    dealerCards.forEach((c, i) => {
        let cardDomElement = document.createElement("img");
    if(i===-1) {
        cardDomElement.src = 'http://127.0.0.1:8080/images/gray_back_reactor.png';
    } else {
        cardDomElement.src = c.image;
    }
    dealerCardsNode.appendChild(cardDomElement)
})

    playerCards.forEach(card => {
        let cardDomElement = document.createElement("img");
    cardDomElement.src = card.image;
    playerCardsNode.appendChild(cardDomElement)
})

    playerScore = computeScore(playerCards);
    if (playerScore === 21) {
        roundWon = true;
        announcementNode.textContent = "BlackJack! You Win!";
    }
    playerScoreNode.textContent = playerScore;

})
.catch(console.error)
}


function newHand() {
  resetPlayingArea();

  //fetch(`https://deckofcardsapi.com/api/deck/${deckID}/draw/?count=4`)
  fetch(`http://localhost:8080/carddeck?shuffled=true&request=4`)
  .then(res => res.json())
  .then(res => {
    hitMeNode.style.display = "block";
    stayNode.style.display = "block";

    dealerCards.push(res[0], res[1])
    playerCards.push(res[2], res[3])

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

    playerCards.forEach(card => {
      let cardDomElement = document.createElement("img");
      cardDomElement.src = card.image;
      playerCardsNode.appendChild(cardDomElement)
    })

    playerScore = computeScore(playerCards);
    if (playerScore === 21) {
      roundWon = true;
      announcementNode.textContent = "BlackJack! You Win!";
    }
    playerScoreNode.textContent = playerScore;

  })
  .catch(console.error)
}


function resetPlayingArea() {
  dealerCards = [];
  playerCards = [];
  roundLost = false;
  roundWon = false;
  roundTied = false;
  dealerScore = "";
  playerScore = 0;
  dealerScoreNode.textContent = dealerScore;
  announcementNode.textContent = "";
  while (dealerCardsNode.firstChild) {
    dealerCardsNode.removeChild(dealerCardsNode.firstChild);
  }
  while (playerCardsNode.firstChild) {
    playerCardsNode.removeChild(playerCardsNode.firstChild);
  }
}

function getNewDeck() {
  resetPlayingArea();
    nextHandNode.style.display = "block";
    hitMeNode.style.display = "none";
    stayNode.style.display = "none";
  // fetch('https://deckofcardsapi.com/api/deck/new/shuffle/?deck_count=1')
  // fetch('https://deckofcardsapi.com/api/deck/new/')
  // .then(res => res.json())
  // .then(res => {
  //   deckID = res.deck_id;
  //   nextHandNode.style.display = "block";
  //   hitMeNode.style.display = "none";
  //   stayNode.style.display = "none";
  // })
  // .catch(console.error)
}

function hitMe(target) {
  if (roundLost || roundWon || roundTied) {return}
  fetch(`https://deckofcardsapi.com/api/deck/${deckID}/draw/?count=1`)
  .then(res => res.json())
  .then(res => {

    // If player
    if (target === 'player') {
      playerCards.push(res.cards[0])
      let cardDomElement = document.createElement("img");
      cardDomElement.src = res.cards[0].image;
      playerCardsNode.appendChild(cardDomElement)

      playerScore = computeScore(playerCards);

      playerScoreNode.textContent = playerScore;
      if (playerScore > 21) {
        roundLost = true;
        announcementNode.textContent = "You broke. Pay up."
      }
    }

    // If dealer
    if (target === 'dealer') {
      let cardDomElement = document.createElement("img");
      dealerCards.push(res.cards[0])
      cardDomElement.src = res.cards[0].image;
      dealerCardsNode.appendChild(cardDomElement)
      dealerPlays();
    }

  })
  .catch(console.log)
}

function dealerPlays() {
  if (roundLost || roundWon || roundTied) {return}
  dealerScore = computeScore(dealerCards);
  dealerScoreNode.textContent = dealerScore;
  dealerCardsNode.firstChild.src = dealerCards[0].image;
  if (dealerScore < 17) {
    setTimeout(()=>hitMe('dealer'), 900)
  }
  else if (dealerScore > 21) {
    roundWon = true;
    announcementNode.textContent = "House broke. You Won the hand!";
  }
  else if (dealerScore > playerScore) {
    roundLost = true;
    announcementNode.textContent = "You Lost the hand...";
  }
  else if (dealerScore === playerScore) {
    roundTied = true;
    announcementNode.textContent = "Tie round.";
  }
  else {
    roundWon = true;
    announcementNode.textContent = "You Won the hand!";
  }
}

function computeScore(cards) {
  let hasAce = false;
  score = cards.reduce((acc, card) => {
    if (card.value === "ACE") {
      hasAce = true;
      return acc + 1
    }
    if (isNaN(card.value)) { return acc + 10 }
    return acc + Number(card.value);
  }, 0)
  if (hasAce) {
    score = (score + 10) > 21 ? score : score + 10;
  }
  return score
}
