import p2 from 'p2';

export function createGrid(width, height, rows, cols) {
  const world = new p2.World({
    gravity: [0, -9.82],
  });

  const particleRadius = 0.5;
  const spacingX = width / (cols + 1);
  const spacingY = height / (rows + 1);

  const bodies = [];
  const springs = [];

  for (let row = 0; row < rows; row++) {
    bodies[row] = [];
    for (let col = 0; col < cols; col++) {
      const x = (col + 1) * spacingX;
      const y = (row + 1) * spacingY;

      const body = new p2.Body({
        mass: 1,
        position: [x, y],
        fixedRotation: true,
      });

      body.addShape(
        new p2.Circle({
          radius: particleRadius,
        })
      );

      world.addBody(body);
      bodies[row][col] = body;
    }
  }

  const leftFixedBody = new p2.Body({
    mass: 0,
    position: [0, height / 2],
  });
  world.addBody(leftFixedBody);

  const rightFixedBody = new p2.Body({
    mass: 0,
    position: [width, height / 2],
  });
  world.addBody(rightFixedBody);

  for (let row = 0; row < rows; row++) {
    for (let col = 0; col < cols; col++) {
      const current = bodies[row][col];

      if (col > 0) {
        const left = bodies[row][col - 1];
        const spring = new p2.LinearSpring(left, current, {
          restLength: spacingX,
          stiffness: 100,
          damping: 1,
        });
        world.addSpring(spring);
        springs.push(spring);
      }

      if (col === 0) {
        const spring = new p2.LinearSpring(leftFixedBody, current, {
          restLength: spacingX,
          stiffness: 100,
          damping: 1,
        });
        world.addSpring(spring);
        springs.push(spring);
      }

      if (col === cols - 1) {
        const spring = new p2.LinearSpring(current, rightFixedBody, {
          restLength: spacingX,
          stiffness: 100,
          damping: 1,
        });
        world.addSpring(spring);
        springs.push(spring);
      }

      if (row > 0) {
        const above = bodies[row - 1][col];
        const spring = new p2.LinearSpring(above, current, {
          restLength: spacingY,
          stiffness: 100,
          damping: 1,
        });
        world.addSpring(spring);
        springs.push(spring);
      }
    }
  }

  const centerRow = Math.floor(rows / 2);
  const centerCol = Math.floor(cols / 2);
  bodies[centerRow][centerCol].applyForce([50, 0], [0, 0]);

  return {
    world,
    bodies,
    springs,
    leftFixedBody,
    rightFixedBody,
    particleRadius,
  };
}

export function renderGrid(context, grid, width, height) {
  const { bodies, springs, particleRadius } = grid;

  context.fillStyle = '#ffffff';
  context.fillRect(0, 0, width, height);

  context.strokeStyle = '#666666';
  context.lineWidth = 1;
  for (const spring of springs) {
    const [x1, y1] = spring.bodyA.position;
    const [x2, y2] = spring.bodyB.position;
    context.beginPath();
    context.moveTo(x1, y1);
    context.lineTo(x2, y2);
    context.stroke();
  }

  context.fillStyle = '#3498db';
  for (const row of bodies) {
    for (const body of row) {
      const [x, y] = body.position;
      context.beginPath();
      context.arc(x, y, particleRadius * 10, 0, Math.PI * 2);
      context.fill();
    }
  }
}
