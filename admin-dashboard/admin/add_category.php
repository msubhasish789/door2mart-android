<?php 
    session_start();
    include ("header.php");?>

<main>
        <div class="section1">
            <div class="container text-center">
                <h1 class="heading-1">
                    Add Your Category
                </h1>
                <div class="row">
                    <div class="col-md-8 mx-auto">
                        <form name="cat_add" action="cat-process.php" method="POST" enctype="multipart/form-data">
                            <div class="mb-3">
                            <label for="inputname" class="form-label" >Category Name</label>
                            <input type="text" name="cat_name" class="form-control"  placeholder="Enter Category Name">
                            </div>
                            <div class="mb-3">
                            <label for="cat_image" class="form-label">Category Image</label>
                            <input type="file" name="cat_image" class="form-control" placeholder="Upload Category image">
                            </div>
                            <br/>
                            <button type="submit" class="btn btn-primary">Submit</button>
                        </form>
                        <br/><br/><br/>
                        <?php 
                            if(isset($_SESSION['msg1']))
                                {
                        ?>
                        <div class="alert alert-success" role="alert">
                        <?php 
                            echo $_SESSION['msg1']; 
                            unset($_SESSION['msg1']);
                        ?>
                        </div>
                        <?php } ?>
                        <?php 
                            if(isset($_SESSION['msg2']))
                                {
                        ?>
                        <div class="alert alert-success" role="alert">
                        <?php 
                            echo $_SESSION['msg2']; 
                            unset($_SESSION['msg2']);
                        ?>
                        </div>
                        <?php } ?>

                    </div>
                </div>

            </div>

        </div>

</main>

<?php 
    include ("footer.php");
?>
