// Tic Tac Toe AI with Minimax Algorithm
// The Coding Train / Daniel Shiffman
// https://thecodingtrain.com/CodingChallenges/154-tic-tac-toe-minimax.html
// https://youtu.be/I64-UTORVfU
// https://editor.p5js.org/codingtrain/sketches/0zyUhZdJD

function bestMove() {
    let bestScore = -Infinity;
    let move;
    for (let i = 0; i < 3; i++) {
        for (let j = 0; j < 3; j++) {
            // Is the spot available?
            if (board[i][j] == "") {
                board[i][j] = ai;
                let score = minimax(board, 10, false);
                board[i][j] = "";
                if (score > bestScore) {
                    bestScore = score;
                    move = { i, j };
                }
            }
        }
    }
    board[move.i][move.j] = ai;
    currentPlayer = human;
}

let scores = {
    X: 1,
    O: -1,
    tie: 0,
};

function minimax(board, depth, isMaximizing) {
    const winner = checkWinner();
    if (winner) return scores[winner];

    if (depth == 0) {
        console.log("idk what to do! the doesn't seem to be a winner?");
        console.log(winner);
        // return scoreTheBoard(board) --> better for x or o?
        // i don't think tic tac toe goes deeper than 10 lol
        return 0;
    }

    // if depth = 0 or node is a terminal node then
    // return the heuristic value of node
    // if (depth == 0 || winner) {
    //     console.log("idk what to do!!", depth, winner);
    //     return -1000;
    // }

    if (isMaximizing) {
        // if maximizingPlayer then
        //     value := −∞
        //     for each child of node do
        //         value := max(value, minimax(child, depth − 1, FALSE))
        //     return value
        let bestScore = -Infinity;
        for (let i = 0; i < 3; i++) {
            for (let j = 0; j < 3; j++) {
                if (board[i][j] == "") {
                    board[i][j] = ai;
                    let currentScore = minimax(board, depth - 1, false);
                    board[i][j] = "";
                    bestScore = max(bestScore, currentScore);
                }
            }
        }
        return bestScore;
    } else {
        // value := +∞
        // for each child of node do
        //     value := min(value, minimax(child, depth − 1, TRUE))
        // return value
        let bestScore = Infinity;
        for (let i = 0; i < 3; i++) {
            for (let j = 0; j < 3; j++) {
                if (board[i][j] == "") {
                    board[i][j] = human;
                    let currentScore = minimax(board, depth - 1, true);
                    board[i][j] = "";
                    bestScore = min(bestScore, currentScore);
                }
            }
        }
        return bestScore;
    }
    return 1;
}
