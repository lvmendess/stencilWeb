const canvas = document.getElementById('mainCanvas');
const ctx = canvas.getContext('2d', { willReadFrequently: true });

const tools = document.querySelectorAll('[data-tool]');
const colorPicker = document.getElementById('colorPicker');
const saveServerBtn = document.getElementById('saveServerBtn');
const canvasForm = document.getElementById('canvasForm');
const hiddenFileInput = document.getElementById('hiddenFileInput');
const alunoData = document.getElementById('alunoData');
const skinUrl = alunoData ? alunoData.dataset.skin : null;
const alunoId = alunoData.dataset.id;
const undoBtn = document.getElementById('undoBtn');
const redoBtn = document.getElementById('redoBtn');

let currentTool = 'pencil';
let currentColor = '#ffffff';
let isDrawing = false;
let lastGridX = -1;
let lastGridY = -1;

const GRID_COLS = 32;
const GRID_ROWS = 64;
const BLOCK_SIZE = 4; // 128/32 = 4, 256/64 = 4

// State Management
let history = [];
let historyStep = -1;

function updateHistoryButtons() {
    undoBtn.disabled = historyStep <= 0;
    redoBtn.disabled = historyStep >= history.length - 1;
}

function saveState() {
    if (historyStep < history.length - 1) {
        history.splice(historyStep + 1);
    }
    history.push(ctx.getImageData(0, 0, canvas.width, canvas.height));
    historyStep++;
    updateHistoryButtons();
}

function undo() {
    if (historyStep > 0) {
        historyStep--;
        ctx.putImageData(history[historyStep], 0, 0);
        updateHistoryButtons();
    }
}

function redo() {
    if (historyStep < history.length - 1) {
        historyStep++;
        ctx.putImageData(history[historyStep], 0, 0);
        updateHistoryButtons();
    }
}

undoBtn.addEventListener('click', undo);
redoBtn.addEventListener('click', redo);

// Tool Selection
tools.forEach(btn => {
    btn.addEventListener('click', () => {
        tools.forEach(t => {
            t.classList.remove('btn-primary');
            t.classList.add('btn-light', 'border-secondary');
        });
        btn.classList.remove('btn-light', 'border-secondary');
        btn.classList.add('btn-primary');
        currentTool = btn.dataset.tool;
    });
});

colorPicker.addEventListener('input', (e) => {
    currentColor = e.target.value;
});

// Utilities
function hexToRgba(hex) {
    const bigint = parseInt(hex.substring(1), 16);
    return {
        r: (bigint >> 16) & 255,
        g: (bigint >> 8) & 255,
        b: bigint & 255,
        a: 255
    };
}

// Flood Fill Algorithm
function floodFill(startX, startY, fillColorHex) {
    const imgData = ctx.getImageData(0, 0, canvas.width, canvas.height);
    const data = imgData.data;

    const startPos = ((startY * BLOCK_SIZE) * canvas.width + (startX * BLOCK_SIZE)) * 4;
    const targetColor = {
        r: data[startPos],
        g: data[startPos + 1],
        b: data[startPos + 2],
        a: data[startPos + 3]
    };

    const fillRgba = hexToRgba(fillColorHex);

    // Optimization: avoid infinite loop if targeting the same color
    if (targetColor.r === fillRgba.r &&
        targetColor.g === fillRgba.g &&
        targetColor.b === fillRgba.b &&
        targetColor.a === fillRgba.a) {
        return;
    }

    const stack = [[startX, startY]];
    const visited = new Set();

    while (stack.length > 0) {
        const [gx, gy] = stack.pop();
        const key = `${gx},${gy}`;

        if (gx < 0 || gx >= GRID_COLS || gy < 0 || gy >= GRID_ROWS || visited.has(key)) continue;
        visited.add(key);

        const pos = ((gy * BLOCK_SIZE) * canvas.width + (gx * BLOCK_SIZE)) * 4;

        // Match against top-left pixel of the 4x4 block
        if (data[pos] === targetColor.r && data[pos + 1] === targetColor.g &&
            data[pos + 2] === targetColor.b && data[pos + 3] === targetColor.a) {

            // Fill entire 4x4 block
            for (let dy = 0; dy < BLOCK_SIZE; dy++) {
                for (let dx = 0; dx < BLOCK_SIZE; dx++) {
                    const fillPos = (((gy * BLOCK_SIZE) + dy) * canvas.width + ((gx * BLOCK_SIZE) + dx)) * 4;
                    data[fillPos] = fillRgba.r;
                    data[fillPos + 1] = fillRgba.g;
                    data[fillPos + 2] = fillRgba.b;
                    data[fillPos + 3] = fillRgba.a;
                }
            }

            stack.push([gx + 1, gy]);
            stack.push([gx - 1, gy]);
            stack.push([gx, gy + 1]);
            stack.push([gx, gy - 1]);
        }
    }

    ctx.putImageData(imgData, 0, 0);
    saveState();
}

// Drawing Logic
function handleDraw(e) {
    const rect = canvas.getBoundingClientRect();

    let clientX, clientY;
    if (e.touches && e.touches.length > 0) {
        clientX = e.touches[0].clientX;
        clientY = e.touches[0].clientY;
    } else {
        clientX = e.clientX;
        clientY = e.clientY;
    }

    // Calculate scale based on physical canvas display size vs internal size
    const scaleX = canvas.width / rect.width;
    const scaleY = canvas.height / rect.height;

    const x = (clientX - rect.left) * scaleX;
    const y = (clientY - rect.top) * scaleY;

    const gridX = Math.floor(x / BLOCK_SIZE);
    const gridY = Math.floor(y / BLOCK_SIZE);

    if (gridX < 0 || gridX >= GRID_COLS || gridY < 0 || gridY >= GRID_ROWS) return;

    if (currentTool === 'bucket') {
        if (e.type === 'mousedown' || e.type === 'touchstart') {
            floodFill(gridX, gridY, currentColor);
        }
        return;
    }

    if (gridX !== lastGridX || gridY !== lastGridY) {
        if (currentTool === 'pencil') {
            ctx.fillStyle = currentColor;
            ctx.globalCompositeOperation = 'source-over';
            ctx.fillRect(gridX * BLOCK_SIZE, gridY * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
        } else if (currentTool === 'eraser') {
            ctx.clearRect(gridX * BLOCK_SIZE, gridY * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
        }
        lastGridX = gridX;
        lastGridY = gridY;
    }
}

// Event Listeners for Interaction
const startDrawing = (e) => {
    if (e.cancelable) e.preventDefault();
    isDrawing = true;
    handleDraw(e);
};

const moveDrawing = (e) => {
    if (e.cancelable) e.preventDefault();
    if (isDrawing) handleDraw(e);
};

const stopDrawing = (e) => {
    if (e && e.cancelable) e.preventDefault();
    if (isDrawing && currentTool !== 'bucket') {
        saveState();
    }
    isDrawing = false;
    lastGridX = -1;
    lastGridY = -1;
};

// Mouse Events
canvas.addEventListener('mousedown', startDrawing);
canvas.addEventListener('mousemove', moveDrawing);
canvas.addEventListener('mouseup', stopDrawing);
canvas.addEventListener('mouseleave', stopDrawing);

// Touch Events
canvas.addEventListener('touchstart', startDrawing, { passive: false });
canvas.addEventListener('touchmove', moveDrawing, { passive: false });
canvas.addEventListener('touchend', stopDrawing, { passive: false });
canvas.addEventListener('touchcancel', stopDrawing, { passive: false });

// File I/O
window.onload = () => {
    if (skinUrl) {
        console.log(skinUrl)
        const img = new Image();
        img.crossOrigin = "Anonymous";
        img.onload = () => {
            ctx.clearRect(0, 0, canvas.width, canvas.height);
            ctx.drawImage(img, 0, 0, canvas.width, canvas.height);
            saveState(); // Initialize undo state with loaded image
        };
        img.onerror = () => {
            console.error("Failed to load existing skin. Initializing blank canvas.");
            saveState();
        };
        img.src = skinUrl;
    } else {
        console.error("skinUrl returned false")
        saveState();
    }
};

window.onload = () => {
    if (skinUrl) {
        const img = new Image();
        img.crossOrigin = "Anonymous";
        img.onload = () => {
            ctx.clearRect(0, 0, canvas.width, canvas.height);
            ctx.drawImage(img, 0, 0, canvas.width, canvas.height);
            saveState();
        };
        img.src = skinUrl;
    } else {
        saveState();
    }
};

saveServerBtn.addEventListener('click', () => {
    saveServerBtn.disabled = true;
    saveServerBtn.textContent = '⏳ Saving...';

    // Extract canvas data as a Blob
    canvas.toBlob((blob) => {
        if (!blob) {
            alert('Failed to generate image data.');
            saveServerBtn.disabled = false;
            saveServerBtn.textContent = '💾 Save to Profile';
            return;
        }

        // 1. Convert Blob to a File object
        const fileName = `canvas_export_${Date.now()}.png`;
        const file = new File([blob], fileName, { type: 'image/png' });

        // 2. Use DataTransfer to append the File to the hidden file input
        const dataTransfer = new DataTransfer();
        dataTransfer.items.add(file);
        hiddenFileInput.files = dataTransfer.files;

        // 3. Submit the form synchronously
        canvasForm.submit();

    }, 'image/png');
});