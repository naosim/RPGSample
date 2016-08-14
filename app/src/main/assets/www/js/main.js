var chara1 = './img/enchant/chara0.png';
var mapAssetName = './img/enchant/map0_0.png';

var walkSpeed = 4;

var Player = function(game) {
  var bear = new Sprite(32, 32);
  bear.image = game.assets[chara1];

  bear.frame = [1,1,2,2,1,1,0,0];   // select sprite frame
  bear.collision = { x: 8, y: 16, width: 16, height: 16 };
  bear.lastPos = {x: 0, y: 0};

  var movingDiff = {x:0, y:0};
  bear.addEventListener('enterframe',function(){ //イベントリスナーを追加する
    var pos = getPos();
    var x = pos.x;
    var y = pos.y;
    if(x % 16 !== 0) {
      x +=movingDiff.x;
    } else {
      if(buttonStatus.right == 'down') {
        movingDiff.x = walkSpeed;
        x += movingDiff.x;
      } else if(x != 0 && buttonStatus.left == 'down') {
        movingDiff.x = -walkSpeed;
        x += movingDiff.x;
      }
    }

    if(y % 16 !== 0) {
      y += movingDiff.y;
    } else {
      if(buttonStatus.down == 'down') {
        movingDiff.y = walkSpeed;
        y += movingDiff.y;
      } else if(y != 0 && buttonStatus.up == 'down') {
        movingDiff.y = -walkSpeed;
        y += movingDiff.y;
      }
    }
    setPos(x, y);

    if(x % 16 == 0 && y % 16 == 0) {
      if(bear.lastPos.x != x || bear.lastPos.y != y) {
        bear.lastPos.x = x;
        bear.lastPos.y = y;
        notifyToNative('position', {fieldName: currentFieldName, x: bear.lastPos.x / 16, y: bear.lastPos.y / 16});
      }
    }
  });

  var getPos = function() {
    return {
      x: bear.x + bear.collision.x,
      y: bear.y + bear.collision.y,
    };
  };

  var getLastPos = function() {
    return {
      x: bear.lastPos.x,
      y: bear.lastPos.y,
    };
  };

  var setPos = function(x, y) {
    bear.x = x - bear.collision.x;
    bear.y = y - bear.collision.y;
  };

  var resetLastPos = function() {
    bear.lastPos = {x: -16, y: -16};
  };

  setPos(0, 0);

  return {
    sprite: bear,
    getPos: getPos,
    getLastPos: getLastPos,
    setPos: setPos,
    resetLastPos: resetLastPos
  };
};


enchant(); // initialize
var game = new Core(240, 240);
game.preload([chara1, mapAssetName]);
game.fps = 20;

var MapGroup = function() {
  var group = new Group();
  var back =  new Map(16, 16);
  var front =  new Map(16, 16);
  group.addChild(back);
  group.addChild(front);
  return {
    group: group,
    back: back,
    front: front,
      // event: new Map(16, 16),
  };
};

var mapGroup = MapGroup();
var player;
var currentFieldName = 'aaa';
game.onload = function(){
  mapGroup.back.image = mapGroup.front.image = game.assets[mapAssetName];
  game.rootScene.addChild(mapGroup.group);


  player = Player(game);
  mapGroup.group.addChild(player.sprite);

  // var last = {
  //   up: false,
  //   down: false,
  //   left: false,
  //   right: false
  // }
  this.addEventListener('enterframe',function(){
    //ブラウザテスト用
    // var run = function(key) {
    //   if(game.input[key] != last[key]) {
    //       if(game.input[key]) {
    //         fromNative.onButtonDown(key);
    //         // console.log(key);
    //       } else {
    //         fromNative.onButtonUp(key);
    //       }
    //   }
    // }
    //
    // run('up');
    // run('down');
    // run('left');
    // run('right');
    //
    // last.up = game.input.up;
    // last.down = game.input.down;
    // last.left = game.input.left;
    // last.right = game.input.right;

    // カメラ位置
    var p = player.getPos();
    var x = Math.min(-p.x, 0) + (game.width - 16) / 2;
    var y = Math.min(-p.y, 0) + (game.height - 16) / 2;
    mapGroup.group.x = x;
    mapGroup.group.y = y;
  });

  notifyToNative('onload');

};// end onload

game.start(); // start your game!

var fromNative = {};
var buttonStatus = {
  up: 'up',
  down: 'up',
  left: 'up',
  right: 'up',
};
var map = {};

fromNative.getPosition = function() {
  var lastPos = player.getLastPos();
  return {fieldName: currentFieldName, x: lastPos.x / 16, y: lastPos.y / 16};
};
fromNative.onButtonDown = function(arrowButtonType) {
  buttonStatus[arrowButtonType] = 'down';
  document.querySelector('#downButtonStatus').innerHTML = buttonStatus.down;
};
fromNative.onButtonUp = function(arrowButtonType) {
  buttonStatus[arrowButtonType] = 'up';
  document.querySelector('#downButtonStatus').innerHTML = buttonStatus.down;
};

fromNative.gotoPosition = function(arg) {
  currentFieldName = arg.fieldName;
  player.resetLastPos();
  player.setPos(arg.x * 16, arg.y * 16);
  updateMapDraw();
};

var updateMapDraw = function() {



  // console.log("updateMapDraw", currentFieldName);
  ['back', 'front'].forEach(fieldLayerName => {
    if(!map[currentFieldName][fieldLayerName]) {
      mapGroup[fieldLayerName].loadData([[]]);
      mapGroup[fieldLayerName].collisionData = null;
      return;
    }
    // console.log(fieldLayerName);
    // clear
    // mapGroup.front.loadData([[]]);
    // mapGroupfront.collisionData = null;

    mapGroup[fieldLayerName].loadData(map[currentFieldName][fieldLayerName].data);
    if(map[currentFieldName][fieldLayerName].collisionData) {
      mapGroup[fieldLayerName].collisionData = map[currentFieldName][fieldLayerName].collisionData;
    } else {
      mapGroup[fieldLayerName].collisionData = map[currentFieldName][fieldLayerName].data.map(a => a.map(() => 0));
    }
  });
};

fromNative.updateFieldLayer = function(arg) {
  var fieldName = arg.fieldName;
  var fieldLayerName = arg.fieldLayerName;
  var fieldData = arg.fieldData;
  var fieldCollisionData = arg.fieldCollisionData;
  if(!map[fieldName]) map[fieldName] = {};
  if(!map[fieldName][fieldLayerName]) map[fieldName][fieldLayerName] = {};
  map[fieldName][fieldLayerName].data = fieldData;
  map[fieldName][fieldLayerName].collisionData = fieldCollisionData;

  updateMapDraw();
};

var notifyToNative = function(methodName, values) {
  if(values) {
    console.log(methodName + '###' + JSON.stringify(values));
  } else {
    console.log(methodName + '###');
  }
};
