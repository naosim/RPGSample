var chara1 = AssetData.chara1;
var mapAssetName = AssetData.mapAssetName;
var walkSpeed = 4;

var isAndroid = navigator.userAgent.toLowerCase().indexOf('android') != -1;

var Player = function(game) {
  var bear = new Sprite(32, 32);
  bear.image = game.assets[chara1];

  bear.frame = [1,1,2,2,1,1,0,0];   // select sprite frame
  bear.collision = { x: 8, y: 16, width: 16, height: 16 };
  bear.lastPos = {x: 0, y: 0};
  bear.direction = 'down';

  var movingDiff = {x:0, y:0};
  var lastIsMoving = false;
  bear.addEventListener('enterframe',function(){ //イベントリスナーを追加する
    var pos = getPos();
    var x = pos.x;
    var y = pos.y;
    var isMoving = false;

    if(x % 16 !== 0) {
      x +=movingDiff.x;
      isMoving = true;
    } else if(buttonStatus.right == 'down') {
      if(!mapGroup.hitTest(x + 16, y)) {
        movingDiff.x = walkSpeed;
        x += movingDiff.x;
        isMoving = true;
      }
      bear.frame = [19,19,20,20,19,19,18,18];
      bear.direction = 'right';
    } else if(buttonStatus.left == 'down') {
      if(x != 0 && !mapGroup.hitTest(x - 16, y)) {
        movingDiff.x = -walkSpeed;
        x += movingDiff.x;
      }
      bear.frame = [10,10,11,11,10,10,9,9];
      bear.direction = 'left';
      isMoving = true;
    }

    if(y % 16 !== 0) {
      y += movingDiff.y;
      isMoving = true;
    } else if(buttonStatus.down == 'down') {
      if(!mapGroup.hitTest(x, y + 16)) {
        movingDiff.y = walkSpeed;
        y += movingDiff.y;
        isMoving = true;
      }
      bear.frame = [1,1,2,2,1,1,0,0];
      bear.direction = 'down';
    } else if(buttonStatus.up == 'down') {
      if(y != 0 && !mapGroup.hitTest(x, y - 16)) {
        movingDiff.y = -walkSpeed;
        y += movingDiff.y;
      }
      bear.frame = [28,28,29,29,28,28,27,27];
      bear.direction = 'up';
      isMoving = true;
    }
    
    if(isMoving) {
      setPos(x, y);
    }

    // 止まった瞬間
    if(lastIsMoving == true && isMoving == false) {
      console.log(bear.direction, bear.frame);
      bear.frame = bear.frame;
    }

    lastIsMoving = isMoving;

    if(x % 16 == 0 && y % 16 == 0) {
      if(bear.lastPos.x != x || bear.lastPos.y != y) {
        bear.lastPos.x = x;
        bear.lastPos.y = y;
        notifyToNative('position', {fieldName: currentFieldName, x: bear.lastPos.x / 16, y: bear.lastPos.y / 16, direction: bear.direction});
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

  var getDirection = function() {
    return bear.direction;
  };

  setPos(0, 0);

  return {
    sprite: bear,
    getPos: getPos,
    getLastPos: getLastPos,
    setPos: setPos,
    resetLastPos: resetLastPos,
    getDirection: getDirection,
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
  back.touchEnabled = false;
  front.touchEnabled = false;
  group.addChild(back);
  group.addChild(front);
  return {
    group: group,
    back: back,
    front: front,
    hitTest: function(x, y) {
//      return false // for debug
      return back.hitTest(x, y) || front.hitTest(x, y);
    },
      // event: new Map(16, 16),
  };
};

var mapGroup = MapGroup();
var player;
var currentFieldName = 'aaa';
game.onload = function(){
  mapGroup.back.image = mapGroup.front.image = game.assets[mapAssetName];
  game.rootScene.addChild(mapGroup.group);


  player = Player(game, mapGroup);
  mapGroup.group.addChild(player.sprite);

  var last = {
    up: false,
    down: false,
    left: false,
    right: false
  };
  this.addEventListener('enterframe',function(){
    //ブラウザテスト用
    var run = function(key) {
      if(game.input[key] != last[key]) {
          if(game.input[key]) {
            fromNative.onButtonDown(key);
            // console.log(key);
          } else {
            fromNative.onButtonUp(key);
          }
      }
    };

    run('up');
    run('down');
    run('left');
    run('right');

    last.up = game.input.up;
    last.down = game.input.down;
    last.left = game.input.left;
    last.right = game.input.right;

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

var lastSetTimeoutId = null;

fromNative.getPosition = function() {
  var lastPos = player.getLastPos();
  return {fieldName: currentFieldName, x: lastPos.x / 16, y: lastPos.y / 16, direction: player.getDirection()};
};
fromNative.onButtonDown = function(arrowButtonType) {
  buttonStatus[arrowButtonType] = 'down';
  game.resume();
};
fromNative.onButtonUp = function(arrowButtonType) {
  buttonStatus[arrowButtonType] = 'up';

  // 節電
  if(isAndroid) {
    if(lastSetTimeoutId) {
      clearTimeout(lastSetTimeoutId);
    }
    lastSetTimeoutId = setTimeout(function(){
      var count = Object
        .keys(buttonStatus)
        .map(function(v) { return buttonStatus[v]; })
        .filter(function(v){ return v != 'up'; })
        .length;
      if(count == 0) {
//         game.fps = 1;
        game.pause();
      }
    }, 3000);
  }
};

fromNative.gotoPosition = function(arg) {
  currentFieldName = arg.fieldName;
  player.resetLastPos();
  player.setPos(arg.x * 16, arg.y * 16);
  updateMapDraw();
};

var updateMapDraw = function() {
  // console.log("updateMapDraw", currentFieldName);
  if(!map[currentFieldName]) {
    return;
  }
  game.resume();
  ['back', 'front'].forEach(function(fieldLayerName) {
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
      mapGroup[fieldLayerName].collisionData = map[currentFieldName][fieldLayerName].data.map(function(a) {
        return a.map(function(){ return 0; });
      });
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

fromNative.call = function(arg) {
  return {'text': 'foo'};
};

var notifyToNative = function(methodName, values) {
  if(values) {
    console.log(methodName + '###' + JSON.stringify(values));
  } else {
    console.log(methodName + '###');
  }
};

console.log('version: 4');
